package ged.web.view.candidate;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RateEvent;
import org.primefaces.model.UploadedFile;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.core.FileUploadService;
import ged.ejb.core.tag.Tag;
import ged.ejb.core.tag.TagService;
import ged.web.core.util.PageEnum;
import ged.web.core.view.AbstractView;

public abstract class AbstractCandidateView extends AbstractView {

	private static final long serialVersionUID = 967686583070407655L;

	protected Candidate candidate;

	@Inject
	protected transient CandidateService candidateService;

	@Inject
	protected transient FileUploadService fileUploadService;

	private List<String> tags;

	@Inject
	protected transient TagService tagService;

	protected String candidateUrl() {
		return PageEnum.CANDIDATE.getRedirectUrl(this.candidate);
	}

	public Candidate getCandidate() {
		return this.candidate;
	}

	public List<String> getTags() {
		return this.tags;
	}

	// TODO: Redo
	protected Set<Tag> getTagsFromStringList(final List<String> labels) {
		if ((labels == null) || labels.isEmpty()) {
			return new HashSet<>();
		}
		final Set<Tag> candidateTags = new HashSet<>();
		for (final String label : labels) {
			final Tag tagFoundInDataBase = this.tagService.findByLabel(label);
			if (tagFoundInDataBase != null) {
				candidateTags.add(tagFoundInDataBase);
			} else {
				candidateTags.add(new Tag(label));
			}
		}
		return candidateTags;
	}

	public void onrate(final RateEvent rateEvent) {
		final Integer rate = (Integer) rateEvent.getRating();
		this.candidate.setRating(rate);
	}

	public void setCandidate(final Candidate candidate) {
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
		try (InputStream inputStream = uploadedFile.getInputstream()) {
			final String uuid = this.fileUploadService.uploadImage(inputStream);
			this.candidate.setImageFileName(uuid);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
