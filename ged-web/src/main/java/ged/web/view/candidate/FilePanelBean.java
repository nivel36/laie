package ged.web.view.candidate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import ged.ejb.ServerFile;
import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.core.FileUploadService;
import ged.web.core.util.Message;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class FilePanelBean extends AbstractBean {

	private static final long serialVersionUID = 6244684784788818815L;

	private Candidate candidate;

	@Inject
	private transient CandidateService candidateService;

	private List<ServerFile> files;

	@Inject
	private transient FileUploadService fileUploadService;

	private ServerFile buildServerFile(final String uuid, final String fileName) {
		final ServerFile file = new ServerFile();
		file.setUuid(uuid);
		file.setName(fileName);
		file.setDate(LocalDate.now());
		file.setCandidate(this.candidate);
		return file;
	}

	private void checkLopdFile() {
		if (!this.hasLopdFile()) {
			Message.addWarning("candidate.warn.no_lopd_file", "candidate.warn.no_lopd_file");
		}
	}

	public List<ServerFile> getFiles() {
		return this.files;
	}

	private boolean hasLopdFile() {
		if (this.files == null) {
			return false;
		}
		for (final ServerFile fileSys : this.files) {
			if (fileSys.isLopd()) {
				return true;
			}
		}
		return false;
	}

	@PostConstruct
	public void init() {
		final String candidateIdValue = this.getValueFromGetParameters("candidateId");
		final Long candidateId = Long.parseLong(candidateIdValue);
		this.candidate = this.candidateService.find(candidateId);
		this.files = this.candidateService.findFilesByCandidateId(candidateId);
		this.checkLopdFile();
	}

	public void onload() {
		this.checkLopdFile();
	}

	public void openFile(final ServerFile file) throws IOException {
		final File fileToOpen = this.fileUploadService.getFileFromFileSystem(file);
		Faces.sendFile(fileToOpen, true);
	}

	public void removeFile(final ServerFile file) {
		this.fileUploadService.removeFileFromFileSystem(file.getUuid());
		this.candidateService.removeFile(file);
		this.files.remove(file);
		this.addInfoMessage("file.message.remove", "file.message.remove", file.getName());
	}

	public void updateFile(final ServerFile file) {
		this.files.remove(file);
		final ServerFile updatedFile = this.candidateService.updateFile(file);
		this.files.add(updatedFile);
		this.addInfoMessage("file.message.update", "file.message.update", updatedFile.getName());
	}

	public void uploadFile(final FileUploadEvent event) {
		final UploadedFile uploadedFile = event.getFile();
		try (InputStream inputStream = uploadedFile.getInputstream()) {
			final String uuid = this.fileUploadService.uploadFile(inputStream);
			final String fileName = uploadedFile.getFileName();
			final ServerFile file = this.buildServerFile(uuid, fileName);
			this.candidateService.addFile(file);
			this.files.add(file);
			this.addInfoMessage("file.message.upload", "file.message.upload", fileName);
		}
		catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
