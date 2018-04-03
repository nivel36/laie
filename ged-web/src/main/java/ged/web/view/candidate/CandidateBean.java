package ged.web.view.candidate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.UploadedServerFile;
import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.core.Address;
import ged.ejb.core.FileUploadService;
import ged.ejb.core.tag.Tag;
import ged.ejb.job.offer.JobCandidature;
import ged.web.core.PageNotFoundException;
import ged.web.core.util.Message;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class CandidateBean extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1577879781927493283L;

	private Candidate candidate;

	private Long candidateId;

	@Inject
	private transient CandidateService candidateService;

	private final List<UploadedServerFile> files = new ArrayList<>();

	@Inject
	private transient FileUploadService fileUploadService;

	private List<JobCandidature> jobCandidatures;

	private List<String> tags = new ArrayList<>();

	private void checkLopdFile() {
		if (!this.hasLopdFile()) {
			Message.addWarning("candidate.warn.no_lopd_file", "candidate.warn.no_lopd_file");
		}
	}

	public Candidate getCandidate() {
		return this.candidate;
	}

	public Long getCandidateId() {
		return this.candidateId;
	}

	public List<UploadedServerFile> getFiles() {
		return this.files;
	}

	public List<JobCandidature> getJobCandidatures() {
		return this.jobCandidatures;
	}

	public List<String> getTags() {
		return this.tags;
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
		if (this.candidateId == null) {
			throw new PageNotFoundException();
		}
		this.candidate = this.candidateService.findAllDataById(this.candidateId);
		if (this.candidate == null) {
			throw new PageNotFoundException();
		}
		if (this.candidate.getAddress() == null) {
			this.candidate.setAddress(new Address());
		}
		for (final Tag tag : this.candidate.getTags()) {
			this.tags.add(tag.getLabel());
		}
		this.files.addAll(this.candidate.getFiles());
		this.jobCandidatures = this.candidate.getJobCandidatures();
		this.checkLopdFile();
	}

	public void onload() {
		this.checkLopdFile();
	}

	public void openFile(final UploadedServerFile file) throws IOException {
		final File fileToOpen = this.fileUploadService.getFileFromFileSystem(file);
		Faces.sendFile(fileToOpen, true);
	}

	public void removeFile(final UploadedServerFile file) {
		this.fileUploadService.removeFileFromFileSystem(file.getUuid());
		this.candidate.getFiles().remove(file);
		this.candidate = this.candidateService.update(this.candidate);
		this.files.remove(file);
		this.addInfoMessage("file.message.remove", "file.message.remove", file.getName());
	}

	private UploadedServerFile saveFile(final String uuid, final String fileName) {
		final UploadedServerFile file = new UploadedServerFile();
		file.setUuid(uuid);
		file.setName(fileName);
		file.setDate(LocalDate.now());
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

	public void setCandidateId(final Long candidateId) {
		this.candidateId = candidateId;
	}

	public void setCandidateService(final CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public void setJobCandidatures(final List<JobCandidature> jobCandidatures) {
		this.jobCandidatures = jobCandidatures;
	}

	public void setTags(final List<String> tags) {
		this.tags = tags;
	}

	public void updateFile(final UploadedServerFile file) {
		this.files.remove(file);
		final UploadedServerFile updatedFile = this.candidateService.updateFile(file);
		this.files.add(updatedFile);
		this.addInfoMessage("file.message.update", "file.message.update", updatedFile.getName());
	}

	public void uploadFile(final FileUploadEvent event) {
		final UploadedFile uploadedFile = event.getFile();
		try (InputStream inputStream = uploadedFile.getInputstream()) {
			final String uuid = this.fileUploadService.uploadFile(inputStream);
			final String fileName = uploadedFile.getFileName();
			final UploadedServerFile file = this.saveFile(uuid, fileName);
			this.files.add(file);
			this.addInfoMessage("file.message.upload", "file.message.upload", fileName);
		}
		catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}