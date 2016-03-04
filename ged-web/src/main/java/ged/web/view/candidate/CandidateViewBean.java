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

	@Inject
	private CandidateService candidateService;

	private boolean editingFile;

	private FileSys file;

	@Inject
	@ConfigurationProperty(value = "file.directory")
	private String fileDirectory;

	private String id;

	private Part part;

	// /////////////////////////////////////////////////////////////////////////
	// INIT
	// /////////////////////////////////////////////////////////////////////////

	public void addFile() {
		this.addingFile = true;
		this.editingFile = false;
	}

	public void cancelAddFile() {
		try {
			this.addingFile = false;
			this.editingFile = false;
			if ((this.file != null) && (this.file.getUuid() != null) && (this.file.getId() == 0)) {
				removeFileFromFileSystem(this.file.getUuid());
			}
		} catch (final IOException e) {
			e.printStackTrace();
			// TODO: faces message
		}
	}

	// /////////////////////////////////////////////////////////////////////////
	// SET AND GET
	// /////////////////////////////////////////////////////////////////////////

	public String editCandidate() {
		this.flash.put("candidate", this.candidate);
		return "candidateEdit?faces-redirect=true";
	}

	public void editFile() {
		this.addingFile = false;
		this.editingFile = true;
	}

	public void editFile(final FileSys file) {
		this.file = file;
		editFile();
	}

	private void error() {
		final NavigationHandler navigationHandler = this.facesContext.getApplication().getNavigationHandler();
		navigationHandler.handleNavigation(this.facesContext, null, "candidateSearch?faces-redirect=true");
		this.facesContext.renderResponse();
	}

	public Candidate getCandidate() {
		return this.candidate;
	}

	public FileSys getFile() {
		return this.file;
	}

	// Extract part name from content-disposition header of part part
	private String getFileName(final Part part) {
		final String partHeader = part.getHeader("content-disposition");
		this.logger.log(Level.FINE, "partHeader: {0}", partHeader);
		for (final String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "").toLowerCase();
			}
		}
		// TODO: throw Exception!
		return null;
	}

	public String getId() {
		return this.id;
	}

	public Part getPart() {
		return this.part;
	}

	public String getStyle() {
		if (isAddingFile() || isEditingFile()) {
			return "z-index: 10";
		}
		return "";
	}

	/**
	 * Not using @PostConstruct because the view is a GET based form.
	 */
	public void init() {
		if (this.id != null) {
			try {
				final Long id = Long.parseLong(this.id);
				this.candidate = this.candidateService.searchCandidateAndCurriculumById(id);
				if (this.candidate == null) {
					error();
				}
			} catch (final NumberFormatException ex) {
				error();
			}
		} else {
			error();
		}
	}

	public boolean isAddingFile() {
		return this.addingFile;
	}

	public boolean isEditingFile() {
		return this.editingFile;
	}

	public String modifyCandidate() {
		this.flash.put("candidate", this.candidate);
		return "candidateEdit?faces-redirect=true";
	}

	// /////////////////////////////////////////////////////////////////////////
	// ACTIONS
	// /////////////////////////////////////////////////////////////////////////

	public void openFile(final FileSys file) {
		try {
			final java.io.File downloableFile = new java.io.File(file.getName());
			new java.io.File(this.fileDirectory, file.getUuid()).renameTo(downloableFile);
			Faces.sendFile(downloableFile, true);
		} catch (final IOException e) {
			e.printStackTrace();
			// TODO: faces message
		}
	}

	public void removeFile(final FileSys file) {
		try {
			removeFileFromFileSystem(file.getUuid());
			this.candidate.getFiles().remove(file);
			this.candidate = this.candidateService.update(this.candidate);
		} catch (final IOException e) {
			e.printStackTrace();
			// TODO: faces message
		}
	}

	// FILE ACTIONS

	private void removeFileFromFileSystem(final String uuid) throws IOException {
		Files.deleteIfExists(new java.io.File(this.fileDirectory, uuid).toPath());
	}

	public void saveFile() {
		this.file.setDate(new Date());
		if (!this.candidate.getFiles().contains(this.file)) {
			this.candidate.getFiles().add(this.file);
			this.file.setCandidate(this.candidate);
		}
		this.candidate = this.candidateService.update(this.candidate);
		this.editingFile = false;
	}

	public void setAddingFile(final boolean addingFile) {
		this.addingFile = addingFile;
	}

	public void setCandidate(final Candidate candidate) {
		this.candidate = candidate;
	}

	public void setCandidateService(final CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public void setEditingFile(final boolean editingFile) {
		this.editingFile = editingFile;
	}

	public void setFile(final FileSys file) {
		this.file = file;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void setPart(final Part part) {
		this.part = part;
	}

	public void upload() {
		try (InputStream input = this.part.getInputStream()) {
			final String fileName = getFileName(this.part);
			final String uuid = UUID.randomUUID().toString();
			Files.copy(input, new java.io.File(this.fileDirectory, uuid).toPath());
			this.addingFile = false;
			this.editingFile = true;
			this.file = new FileSys();
			this.file.setUuid(uuid);
			this.file.setName(fileName);
		} catch (final IOException ex) {
			ex.printStackTrace();
			// TODO: faces message
		}
	}
}
