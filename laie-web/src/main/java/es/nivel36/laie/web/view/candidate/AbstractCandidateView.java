package es.nivel36.laie.web.view.candidate;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import org.omnifaces.cdi.Param;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.candidate.CandidateService;
import es.nivel36.laie.ejb.core.file.File;
import es.nivel36.laie.ejb.core.file.FileService;
import es.nivel36.laie.ejb.core.file.FileUploadException;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.tag.TagService;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserService;
import es.nivel36.laie.web.core.view.AbstractView;
import jakarta.inject.Inject;

public abstract class AbstractCandidateView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(AbstractCandidateView.class);

	private static final long serialVersionUID = 6785796173827142302L;

	protected @Param Candidate candidate;

	protected File candidateImage;

	protected boolean imageChanged;
	
	protected Integer rating;
	
	protected transient List<String> tags;
	
	protected transient @Inject CandidateService candidateService;

	protected transient @Inject FileService fileService;
	
	protected transient @Inject TagService tagService;

	protected transient @Inject UserService userService;

	protected String candidateUrl() {
		return ViewCandidateView.getUrl(this.candidate.getId());
	}

	public void deleteImage() {
		logger.trace("Delete user image");
		this.candidateImage = null;
		this.candidate.setPicture(candidateImage);
		this.imageChanged = true;
	}

	public List<User> queryOwner(final String query) {
		return this.userService.search(query, Page.FIRST_TEN_RESULTS).getResultData();
	}

	protected void saveImage() {
		if (!this.imageChanged) {
			return;
		}
		if (this.imageChanged && this.candidateImage == null) {
			this.candidate.setPicture(null);
			return;
		}
		logger.trace("Changing user image");
		try (final InputStream is = this.fileService.downloadFile(candidateImage);
				final BufferedInputStream bis = new BufferedInputStream(is)) {
			this.candidate = this.candidateService.changeCandidatesImage(this.candidate, bis);
		} catch (final IOException e) {
			throw new FileUploadException(e);
		}
	}
	
	public void uploadImage(final FileUploadEvent event) {
		Objects.requireNonNull(event);
		this.imageChanged = true;
		final UploadedFile uploadedFile = event.getFile();
		if (uploadedFile == null) {
			return;
		}
		logger.debug("Upload candidate {} image action performed", this.candidate);
		try (final InputStream inputStream = uploadedFile.getInputStream()) {
			this.candidateImage = this.fileService.uploadTemporalFile(inputStream);
		} catch (final IOException e) {
			throw new FileUploadException(e);
		}
	}
	
	public Candidate getCandidate() {
		return this.candidate;
	}

	public File getCandidateImage() {
		return candidateImage;
	}

	public Integer getRating() {
		return rating;
	}

	public List<String> getTags() {
		return this.tags;
	}

	public void setCandidate(final Candidate candidate) {
		this.candidate = candidate;
	}
	
	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public void setTags(final List<String> tags) {
		this.tags = tags;
	}

	public void setCandidateService(final CandidateService candidateService) {
		Objects.requireNonNull(candidateService);
		this.candidateService = candidateService;
	}

	public void setFileService(final FileService fileService) {
		Objects.requireNonNull(fileService);
		this.fileService = fileService;
	}

	public void setTagService(final TagService tagService) {
		Objects.requireNonNull(tagService);
		this.tagService = tagService;
	}
}
