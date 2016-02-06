package ged.web.view.candidate;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;

import javax.faces.application.NavigationHandler;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import org.omnifaces.util.Faces;

import ged.ejb.core.util.ConfigurationProperty;
import ged.ejb.service.candidate.Candidate;
import ged.ejb.service.candidate.CandidateService;
import ged.ejb.service.candidate.FileSys;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class CandidateViewBean extends AbstractBean {

	private static final long serialVersionUID = 1577879781927493283L;

	private boolean addingFile;

	private Candidate candidate;

	private String candidateId;
	
	@Inject
	private CandidateService candidateService;

	private boolean editingFile;

	private FileSys file;

	@Inject
	@ConfigurationProperty(value = "file.directory")
	private String fileDirectory;

	private Part part;

	// /////////////////////////////////////////////////////////////////////////
	// INIT
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Not using @PostConstruct because the view is a GET based form.
	 */
	public void init() {
		if (candidateId != null) {
			try {
				Long id = Long.parseLong(candidateId);
				candidate = candidateService.searchCandidateAndCurriculumById(id);
				if (candidate == null) {
					error();
				}
			} catch (NumberFormatException ex) {
				error();
			}
		} else {
			error();
		}
	}

	private void error() {
		NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
		navigationHandler.handleNavigation(facesContext, null, "candidateSearch?faces-redirect=true");
		facesContext.renderResponse();
	}

	// /////////////////////////////////////////////////////////////////////////
	// SET AND GET
	// /////////////////////////////////////////////////////////////////////////

	public Candidate getCandidate() {
		return candidate;
	}

	public String getCandidateId() {
		return candidateId;
	}

	public FileSys getFile() {
		return file;
	}

	public Part getPart() {
		return part;
	}

	public boolean isAddingFile() {
		return addingFile;
	}

	public boolean isEditingFile() {
		return editingFile;
	}

	public void setAddingFile(boolean addingFile) {
		this.addingFile = addingFile;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public void setCandidateService(CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public void setEditingFile(boolean editingFile) {
		this.editingFile = editingFile;
	}

	public void setFile(FileSys file) {
		this.file = file;
	}

	public void setPart(Part part) {
		this.part = part;
	}
	
	public String getStyle() {
		if(isAddingFile() || isEditingFile() ) {
			return "z-index: 10";
		}
		return "";
	}

	// /////////////////////////////////////////////////////////////////////////
	// ACTIONS
	// /////////////////////////////////////////////////////////////////////////

	public String editCandidate() {
		flash.put("candidate", candidate);
		return "candidateEdit?faces-redirect=true";
	}

	public String modifyCandidate() {
		flash.put("candidate", candidate);
		return "candidateEdit?faces-redirect=true";
	}

	// FILE ACTIONS

	public void addFile() {
		addingFile = true;
		editingFile = false;
	}

	public void editFile() {
		addingFile = false;
		editingFile = true;
	}

	public void editFile(FileSys file) {
		this.file = file;
		editFile();
	}

	public void cancelAddFile() {
		try {
			addingFile = false;
			editingFile = false;
			if (file.getUuid() != null && file.getId() == 0) {
				removeFileFromFileSystem(file.getUuid());
			}
		} catch (IOException e) {
			e.printStackTrace();
			// TODO: faces message
		}
	}

	public void openFile(FileSys file) {
		try {
			java.io.File downloableFile = new java.io.File(file.getName());
			new java.io.File(fileDirectory, file.getUuid()).renameTo(downloableFile);
			Faces.sendFile(downloableFile, true);
		} catch (IOException e) {
			e.printStackTrace();
			// TODO: faces message
		}
	}

	public void removeFile(FileSys file) {
		try {
			removeFileFromFileSystem(file.getUuid());
			candidate.getFiles().remove(file);
			candidate = candidateService.update(candidate);
		} catch (IOException e) {
			e.printStackTrace();
			// TODO: faces message
		}
	}

	public void saveFile() {
		file.setDate(new Date());
		if (!candidate.getFiles().contains(file)) {
			candidate.getFiles().add(file);
			file.setCandidate(candidate);
		}
		candidate = candidateService.update(candidate);
		editingFile = false;
	}

	public void upload() {
		try (InputStream input = part.getInputStream()) {
			String fileName = getFileName(part);
			String uuid = UUID.randomUUID().toString();
			Files.copy(input, new java.io.File(fileDirectory, uuid).toPath());
			addingFile = false;
			editingFile = true;
			file = new FileSys();
			file.setUuid(uuid);
			file.setName(fileName);
		} catch (IOException ex) {
			ex.printStackTrace();
			// TODO: faces message
		}
	}

	private void removeFileFromFileSystem(String uuid) throws IOException {
		Files.deleteIfExists(new java.io.File(fileDirectory, uuid).toPath());
	}

	// Extract part name from content-disposition header of part part
	private String getFileName(Part part) {
		final String partHeader = part.getHeader("content-disposition");
		logger.log(Level.FINE, "partHeader: {0}", partHeader);
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "").toLowerCase();
			}
		}
		// TODO: throw Exception!
		return null;
	}
}
