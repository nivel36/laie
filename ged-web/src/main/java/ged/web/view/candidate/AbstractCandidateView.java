package ged.web.view.candidate;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.List;

import javax.inject.Inject;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RateEvent;
import org.primefaces.model.UploadedFile;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.core.FileUploadService;
import ged.ejb.core.model.Page;
import ged.ejb.core.tag.Tag;
import ged.ejb.core.tag.TagService;
import ged.web.core.util.PageEnum;
import ged.web.core.view.AbstractView;

public abstract class AbstractCandidateView extends AbstractView {

	private static final long serialVersionUID = 1L;

	protected Candidate candidate;

	@Inject
	protected transient CandidateService candidateService;

	@Inject
	protected transient FileUploadService fileUploadService;

	protected List<Tag> tags;

	@Inject
	protected transient TagService tagService;

	protected String candidateUrl() {
		return PageEnum.CANDIDATE.getRedirectedUrl(this.candidate);
	}

	public Candidate getCandidate() {
		return this.candidate;
	}

	public List<Tag> getTags() {
		return this.tags;
	}

	public void onrate(final RateEvent rateEvent) {
		final Integer rate = (Integer) rateEvent.getRating();
		this.candidate.setRating(rate);
	}

	public List<Tag> queryTags(final String query) {
		return this.tagService.search(query, Page.TEN_RESULTS_PER_PAGE).getResultData();
	}

	public void setCandidate(final Candidate candidate) {
		this.candidate = candidate;
	}

	public void setCandidateService(final CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public void setTags(final List<Tag> tags) {
		this.tags = tags;
	}

	public void uploadImage(final FileUploadEvent event) {
		final UploadedFile uploadedFile = event.getFile();
		try (InputStream inputStream = uploadedFile.getInputstream()) {
			final String uuid = this.fileUploadService.uploadImage(inputStream);
			this.candidate.setImageFileName(uuid);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
