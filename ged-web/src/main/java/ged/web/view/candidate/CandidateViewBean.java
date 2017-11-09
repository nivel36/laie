package ged.web.view.candidate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.candidate.UploadedServerFile;
import ged.ejb.core.tag.Tag;
import ged.web.core.util.ConfigurationProperty;
import ged.web.core.util.MessageUtils;
import ged.web.core.util.Navigate;
import ged.web.core.view.AbstractPageBean;

@Named
@ViewScoped
public class CandidateViewBean extends AbstractPageBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1577879781927493283L;

	private Candidate candidate;

	@Inject
	private transient CandidateService candidateService;

	private boolean editable;

	@Inject
	@ConfigurationProperty(value = "file.directory")
	private String fileDirectory;

	private final List<UploadedServerFile> files = new ArrayList<>();

	private String id;

	private List<String> tags = new ArrayList<>();

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

	private void error() {
		Navigate.toPage("candidateSearch");
	}

	public Candidate getCandidate() {
		return this.candidate;
	}

	public List<UploadedServerFile> getFiles() {
		return this.files;
	}

	public String getId() {
		return this.id;
	}

	public List<String> getTags() {
		return this.tags;
	}

	private Set<Tag> getTagsFromStringList(final List<String> labels) {
		final List<Tag> allTags = this.candidateService.findAllTags();
		final Set<Tag> candidateTags = new HashSet<>();
		for (final String label : labels) {
			if (label == null) {
				return null;
			}
			boolean found = false;
			for (final Tag tag : allTags) {
				if (tag.getLabel().equals(label)) {
					candidateTags.add(tag);
					found = true;
					break;
				}
			}
			if (!found) {
				final Tag tag = new Tag();
				tag.setLabel(label);
				candidateTags.add(tag);
			}
		}
		return candidateTags;
	}

	private boolean hasLopdFile() {
		if (this.candidate.getFiles() == null) {
			return false;
		}
		for (final UploadedServerFile fileSys : this.candidate.getFiles()) {
			if (fileSys.isLopd()) {
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
				final long candidateId = Long.parseLong(this.id);
				this.candidate = this.candidateService.findCandidateAndFiles(candidateId);
				if (this.candidate == null) {
					error();
				}
				for (final Tag tag : this.candidate.getTags()) {
					this.tags.add(tag.getLabel());
				}
				this.files.addAll(this.candidate.getFiles());
			} catch (final NumberFormatException ex) {
				error();
			}
		} else {
			error();
		}
		checkLopdFile();
	}

	public boolean isEditable() {
		return this.editable;
	}

	public String modifyCandidate() {
		this.flash.put("candidate", this.candidate);
		return "candidateEdit?faces-redirect=true";
	}

	public void onload() {
		checkLopdFile();
	}

	public void openFile(final UploadedServerFile file) {
		try {
			final File downloableFile = new File(file.getName());
			new java.io.File(this.fileDirectory, file.getUuid()).renameTo(downloableFile);
			Faces.sendFile(downloableFile, true);
		} catch (final IOException e) {
			logger.error("Can't open file", e);
			MessageUtils.addErrorMessage("error.unnexpected_error", "error.unnexpected_error");
		}
	}

	public void removeFile(final UploadedServerFile file) {
		try {
			removeFileFromFileSystem(file.getUuid());
			this.candidate.getFiles().remove(file);
			this.candidate = this.candidateService.save(this.candidate);
			this.files.remove(file);
			addInfoMessage("file.message.remove", "file.message.remove", file.getName());
		} catch (final IOException e) {
			logger.error("Can't remove file", e);
			MessageUtils.addErrorMessage("error.unnexpected_error", "error.unnexpected_error");
		}
	}

	private void removeFileFromFileSystem(final String uuid) throws IOException {
		Files.deleteIfExists(new java.io.File(this.fileDirectory, uuid).toPath());
	}

	public void saveCandidate() {
		this.candidate.setTags(getTagsFromStringList(this.tags));
		this.candidate.setFiles(new HashSet<>(this.files));
		this.candidate = this.candidateService.save(this.candidate);
		this.editable = false;
	}

	private UploadedServerFile saveFile(final String uuid, final String fileName) {
		final UploadedServerFile file = new UploadedServerFile();
		file.setUuid(uuid);
		file.setName(fileName);
		file.setDate(new Date());
		if (!this.candidate.getFiles().contains(file)) {
			this.candidate.getFiles().add(file);
			file.setCandidate(this.candidate);
		}
		this.candidateService.insertFile(file);
		return file;
	}

	public void setCandidate(final Candidate candidate) {
		this.candidate = candidate;
	}

	public void setCandidateService(final CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public void setFileDirectory(final String fileDirectory) {
		this.fileDirectory = fileDirectory;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void setTags(final List<String> tags) {
		this.tags = tags;
	}

	public void undelete() {
		logger.debug("UNDELETE action");
		this.candidate.setDeleted(false);
		this.candidate = this.candidateService.save(this.candidate);
	}

	public void updateFile(final UploadedServerFile file) {
		this.files.remove(file);
		final UploadedServerFile updatedFile = this.candidateService.updateFile(file);
		this.files.add(updatedFile);
		addInfoMessage("file.message.update", "file.message.update", updatedFile.getName());
	}

	private String upload(final UploadedFile file) {
		final String uuid = UUID.randomUUID().toString();
		try (InputStream input = file.getInputstream()) {
			Files.copy(input, new java.io.File(this.fileDirectory, uuid).toPath());
		} catch (final IOException ex) {
			logger.error("Can't upload file", ex);
			MessageUtils.addErrorMessage("error.unnexpected_error", "error.unnexpected_error");
		}
		return uuid;
	}

	public void uploadFile(final FileUploadEvent event) {
		final String uuid = upload(event.getFile());
		final UploadedServerFile file = saveFile(uuid, event.getFile().getFileName());
		this.files.add(file);
		addInfoMessage("file.message.upload", "file.message.upload", event.getFile().getFileName());
	}
}