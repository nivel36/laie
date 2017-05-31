package ged.web.view.candidate;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import javax.faces.application.NavigationHandler;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.core.util.ConfigurationProperty;
import ged.ejb.curriculum.FileSys;
import ged.web.core.util.MessageUtils;
import ged.web.core.view.AbstractPageBean;

@Named
@ViewScoped
public class CandidateViewBean extends AbstractPageBean {

	private static final transient Logger logger = LoggerFactory.getLogger(CandidateViewBean.class.getName());

	private static final long serialVersionUID = 1577879781927493283L;

	private boolean addingFile;

	private Candidate candidate;

	private final transient CandidateService candidateService;

	private boolean editable;

	private boolean editingFile;

	private FileSys file;

	private final String fileDirectory;

	private String id;

	private transient Part part;

	@Inject
	public CandidateViewBean(final CandidateService candidateService,
			@ConfigurationProperty(value = "file.directory") final String fileDirectory) {
		Objects.requireNonNull(candidateService);
		Objects.requireNonNull(fileDirectory);
		this.candidateService = candidateService;
		this.fileDirectory = fileDirectory;
	}

	public void addFile() {
		this.addingFile = true;
		this.editingFile = false;
		this.facesContext.getExternalContext().getFlash().setKeepMessages(true);
	}

	public void cancelAddFile() {
		try {
			this.addingFile = false;
			this.editingFile = false;
			if (temporaryFileUploaded()) {
				removeFileFromFileSystem(this.file.getUuid());
			}
		} catch (final IOException e) {
			logger.error("Can't remove file", e);
			MessageUtils.addErrorMessage("error.unnexpected_error", "error.unnexpected_error");
		}
	}

	public void cancelEditCandidate() {
		this.editable = false;
	}

	private void checkLopdFile() {
		if (!hasLopdFile()) {
			MessageUtils.addWarningMessage("candidate.warn.no_lopd_file", "candidate.warn.no_lopd_file");
		}
	}

	public void editCandidate() {
		this.editable = true;
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
	private String getFileName(final Part part) throws IOException {
		final String partHeader = part.getHeader("content-disposition");
		logger.debug("partHeader: {}", partHeader);
		for (final String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "").toLowerCase();
			}
		}
		throw new IOException("Name not found");
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

	private boolean hasLopdFile() {
		if (this.candidate.getFiles() == null) {
			return false;
		}
		for (final FileSys fileSys : this.candidate.getFiles()) {
			if ("lopd".equals(fileSys.getFileType())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Not using @PostConstruct because the view is a GET based form.
	 */
	public void init() {
		if (this.id != null) {
			try {
				final Long candidateId = Long.parseLong(this.id);
				this.candidate = this.candidateService.findCandidateAndFiles(candidateId);
				if (this.candidate == null) {
					error();
				}
			} catch (final NumberFormatException ex) {
				error();
			}
		} else {
			error();
		}
		checkLopdFile();
	}

	public boolean isAddingFile() {
		return this.addingFile;
	}

	public boolean isEditable() {
		return this.editable;
	}

	public boolean isEditingFile() {
		return this.editingFile;
	}

	public String modifyCandidate() {
		this.flash.put("candidate", this.candidate);
		return "candidateEdit?faces-redirect=true";
	}

	public void onload() {
		checkLopdFile();
	}

	public void openFile(final FileSys file) {
		try {
			final java.io.File downloableFile = new java.io.File(file.getName());
			new java.io.File(this.fileDirectory, file.getUuid()).renameTo(downloableFile);
			Faces.sendFile(downloableFile, true);
		} catch (final IOException e) {
			logger.error("Can't open file", e);
			MessageUtils.addErrorMessage("error.unnexpected_error", "error.unnexpected_error");
		}
	}

	public void removeFile(final FileSys file) {
		try {
			removeFileFromFileSystem(file.getUuid());
			this.candidate.getFiles().remove(file);
			this.candidate = this.candidateService.save(this.candidate);
		} catch (final IOException e) {
			logger.error("Can't remove file", e);
			MessageUtils.addErrorMessage("error.unnexpected_error", "error.unnexpected_error");
		}
	}

	private void removeFileFromFileSystem(final String uuid) throws IOException {
		Files.deleteIfExists(new java.io.File(this.fileDirectory, uuid).toPath());
	}

	public void saveCandidate() {
		this.candidate.setUser(this.sessionBean.getUser());
		this.candidateService.save(this.candidate);
		this.editable = false;
	}

	public void saveFile() {
		this.file.setDate(new Date());
		if (!this.candidate.getFiles().contains(this.file)) {
			this.candidate.getFiles().add(this.file);
			this.file.setCandidate(this.candidate);
		}
		this.candidate.setUser(this.sessionBean.getUser());
		this.candidate = this.candidateService.save(this.candidate);
		this.editingFile = false;
	}

	public void setAddingFile(final boolean addingFile) {
		this.addingFile = addingFile;
	}

	public void setCandidate(final Candidate candidate) {
		this.candidate = candidate;
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

	private boolean temporaryFileUploaded() {
		return this.file != null && this.file.getUuid() != null && this.file.getId() == 0;
	}

	public void undelete() {
		logger.debug("UNDELETE action");
		this.candidate.setDeleted(false);
		this.candidateService.save(this.candidate);
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
			logger.error("Can't upload file", ex);
			MessageUtils.addErrorMessage("error.unnexpected_error", "error.unnexpected_error");
		}
	}
}