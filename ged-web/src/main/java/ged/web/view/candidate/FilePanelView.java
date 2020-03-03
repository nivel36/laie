package ged.web.view.candidate;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;
import org.omnifaces.util.Faces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.core.file.File;
import ged.ejb.core.file.FileService;
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

	private List<File> files;

	@Inject
	private transient FileService fileUploadService;

	private File buildServerFile(final String uuid, final String fileName) {
		final File file = new File();
		file.setUuid(uuid);
		file.setName(fileName);
		file.setCreated(LocalDateTime.now());
		file.setCandidate(this.candidate);
		return file;
	}

	public List<File> getFiles() {
		return this.files;
	}

	@PostConstruct
	public void init() {
		this.files = this.candidateService.findFiles(this.candidate);
	}

	public void openFile(final File file) throws IOException {
		final InputStream is = this.fileUploadService.getFile(file.getUuid());
		Faces.sendFile(is, file.getName(), true);
	}

	public void removeFile(final File file) {
		this.fileUploadService.removeFile(file.getUuid());
		this.candidateService.removeFile(file);
		this.files.remove(file);
	}

	public void uploadFile(final FileUploadEvent event) {
		final UploadedFile uploadedFile = event.getFile();
		try (InputStream inputStream = uploadedFile.getInputstream()) {
			final String uuid = this.fileUploadService.uploadFile(inputStream);
			final String fileName = uploadedFile.getFileName();
			final File file = this.buildServerFile(uuid, fileName);
			this.candidateService.addFileToCandidate(this.candidate, file);
			this.files.add(file);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
