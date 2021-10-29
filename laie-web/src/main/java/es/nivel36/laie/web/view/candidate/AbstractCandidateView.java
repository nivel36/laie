package es.nivel36.laie.web.view.candidate;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.List;

import javax.inject.Inject;

import org.omnifaces.cdi.Param;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RateEvent;
import org.primefaces.model.file.UploadedFile;

import es.nivel36.laie.ejb.candidate.CandidateDto;
import es.nivel36.laie.ejb.candidate.CandidateService;
import es.nivel36.laie.ejb.core.file.FileService;
import es.nivel36.laie.ejb.core.tag.TagService;
import es.nivel36.laie.web.core.util.PageEnum;
import es.nivel36.laie.web.core.view.AbstractView;

public abstract class AbstractCandidateView extends AbstractView {

	private static final long serialVersionUID = 1L;

	@Inject
	@Param(name = "uid", required = true)
	protected CandidateDto candidate;

	@Inject
	protected transient CandidateService candidateService;

	@Inject
	protected transient FileService fileUploadService;

	protected transient List<String> tags;

	@Inject
	protected transient TagService tagService;

	protected String candidateUrl() {
		return this.navigator.getRedirectUrl(PageEnum.CANDIDATE, this.candidate.getUid());
	}

	public CandidateDto getCandidate() {
		return this.candidate;
	}

	public List<String> getTags() {
		return this.tags;
	}

	public void onrate(final RateEvent<Integer> rateEvent) {
		final Integer rate = rateEvent.getRating();
		this.candidate.setRating(rate);
	}

	public void setCandidate(final CandidateDto candidate) {
		this.candidate = candidate;
	}

	public void setCandidateService(final CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public void setTags(final List<String> tags) {
		this.tags = tags;
	}

	public void uploadImage(final FileUploadEvent event) {
		final UploadedFile uploadedFile = event.getFile();
		try (final InputStream inputStream = uploadedFile.getInputStream()) {
			final String path = this.candidateService.changeUsersImage(this.candidate.getUid(), inputStream);
			this.candidate.setPicturesPath(path);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
