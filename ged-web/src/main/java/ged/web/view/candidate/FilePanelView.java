package ged.web.view.candidate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;
import org.omnifaces.util.Faces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.core.FileService;
import ged.ejb.core.file.ServerFile;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class FilePanelView extends AbstractView {

	private static final long serialVersionUID = 1L;

	@Inject
	@Param(name = "id", required = true)
	private Candidate candidate;

	@Inject
	private transient CandidateService candidateService;

	private List<ServerFile> files;

	@Inject
	private transient FileService fileUploadService;

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
			this.addMessage(FacesMessage.SEVERITY_WARN, "candidate.warn.no_lopd_file", "candidate.warn.no_lopd_file");
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
		this.files = this.candidateService.findFiles(this.candidate);
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
	}

	public void uploadFile(final FileUploadEvent event) {
		final UploadedFile uploadedFile = event.getFile();
		try (InputStream inputStream = uploadedFile.getInputstream()) {
			final String uuid = this.fileUploadService.uploadFile(inputStream);
			final String fileName = uploadedFile.getFileName();
			final ServerFile file = this.buildServerFile(uuid, fileName);
			this.candidateService.addFileToCandidate(this.candidate, file);
			this.files.add(file);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
