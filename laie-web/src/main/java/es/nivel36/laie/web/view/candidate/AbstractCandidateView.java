package es.nivel36.laie.web.view.candidate;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import org.omnifaces.cdi.Param;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RateEvent;
import org.primefaces.model.file.UploadedFile;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.candidate.CandidateService;
import es.nivel36.laie.ejb.core.file.FileService;
import es.nivel36.laie.ejb.core.tag.Tag;
import es.nivel36.laie.web.core.view.AbstractView;

public abstract class AbstractCandidateView extends AbstractView {

	private static final long serialVersionUID = 6785796173827142302L;

	@Param
	protected Candidate candidate;

	protected transient List<Tag> tags;

	@Inject
	protected transient CandidateService candidateService;

	@Inject
	protected transient FileService fileService;

	public void uploadImage(final FileUploadEvent event) {
		final UploadedFile uploadedFile = event.getFile();
		try (final InputStream inputStream = uploadedFile.getInputStream()) {
			this.candidateService.changeCandidatesImage(this.candidate, inputStream);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public void onrate(final RateEvent<Integer> rateEvent) {
		final Integer rate = rateEvent.getRating();
		this.candidate.setRating(rate);
	}

	protected String candidateUrl() {
		return "/candidate/view?faces-redirect=true&candidate=" + this.candidate.getId();
	}

	public Candidate getCandidate() {
		return this.candidate;
	}

	public List<Tag> getTags() {
		return this.tags;
	}

	public void setCandidate(final Candidate candidate) {
		this.candidate = candidate;
	}

	public void setTags(final List<Tag> tags) {
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
}
