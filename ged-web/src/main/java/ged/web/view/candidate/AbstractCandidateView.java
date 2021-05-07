package ged.web.view.candidate;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.List;

import javax.inject.Inject;

import org.omnifaces.cdi.Param;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RateEvent;
import org.primefaces.model.file.UploadedFile;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.core.file.File;
import ged.ejb.core.file.FileService;
import ged.ejb.core.model.Page;
import ged.ejb.core.tag.Tag;
import ged.ejb.core.tag.TagService;
import ged.web.core.util.PageEnum;
import ged.web.core.view.AbstractView;

public abstract class AbstractCandidateView extends AbstractView {

	private static final long serialVersionUID = 1L;

	@Inject
	@Param(name = "id", required = true)
	protected Candidate candidate;

	@Inject
	protected transient CandidateService candidateService;

	@Inject
	protected transient FileService fileUploadService;

	protected transient List<Tag> tags;

	@Inject
	protected transient TagService tagService;

	protected String candidateUrl() {
		return this.navigator.getRedirectUrl(PageEnum.CANDIDATE, this.candidate);
	}

	public Candidate getCandidate() {
		return this.candidate;
	}

	public List<Tag> getTags() {
		return this.tags;
	}

	public void onrate(final RateEvent<Integer> rateEvent) {
		final Integer rate = rateEvent.getRating();
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
		try (InputStream inputStream = uploadedFile.getInputStream()) {
			File file = this.fileUploadService.uploadFile(inputStream, this.candidate.getUid() + "_picture", true);
			this.candidate.setPicture(file);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
