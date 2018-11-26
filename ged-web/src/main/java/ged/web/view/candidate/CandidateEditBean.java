package ged.web.view.candidate;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RateEvent;
import org.primefaces.model.UploadedFile;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.candidate.Origin;
import ged.ejb.core.Address;
import ged.ejb.core.FileUploadService;
import ged.ejb.core.model.Page;
import ged.ejb.core.tag.Tag;
import ged.ejb.core.tag.TagService;
import ged.web.core.view.AbstractDialogBean;

@Named
@ViewScoped
public class CandidateEditBean extends AbstractDialogBean {

	private static final long serialVersionUID = -7336942836739051499L;

	private Candidate candidate;

	@Inject
	private transient CandidateService candidateService;

	@Inject
	private transient FileUploadService fileUploadService;

	private List<String> tags = new ArrayList<>();

	@Inject
	private transient TagService tagService;

	public Candidate buildNewCandidate() {
		final Candidate newCandidate = new Candidate();
		final Address address = new Address();
		newCandidate.setAddress(address);
		newCandidate.setOwner(this.sessionUser.get());
		return newCandidate;
	}

	public Candidate getCandidate() {
		return this.candidate;
	}

	public List<String> getTags() {
		return this.tags;
	}

	// TODO: Redo
	private Set<Tag> getTagsFromStringList(final List<String> labels) {
		if ((labels == null) || labels.isEmpty()) {
			return new HashSet<>();
		}
		final Set<Tag> candidateTags = new HashSet<>();
		for (final String label : labels) {
			final List<Tag> tagsFoundInDataBase = this.tagService.search(label, Page.ALL);
			final Tag tag;
			if (tagsFoundInDataBase.size() == 1) {
				tag = tagsFoundInDataBase.get(0);
			}
			else {
				tag = new Tag();
				tag.setLabel(label);
			}
			candidateTags.add(tag);
		}
		return candidateTags;
	}

	@PostConstruct
	public void init() {
		this.candidate = this.getValueFromFlash("candidate");
		if (this.candidate == null) {
			this.candidate = this.buildNewCandidate();
		}
		if (this.candidate.getOrigin() == null) {
			this.candidate.setOrigin(new Origin());
		}
		for (final Tag tag : this.candidate.getTags()) {
			this.tags.add(tag.getLabel());
		}
	}

	public boolean isNewCandidate() {
		return this.candidate.getId() == 0;
	}

	public void onChangeOrigin() {
		final Origin origin = this.candidate.getOrigin();
		if (!origin.getCode().equals("other")) {
			origin.setOther(null);
		}
	}

	public void onrate(final RateEvent rateEvent) {
		final Integer rate = (Integer) rateEvent.getRating();
		this.candidate.setRating(rate);
	}

	public String save() {
		this.candidate.setTags(this.getTagsFromStringList(this.tags));
		this.candidate = this.candidateService.save(this.candidate);
		return "/faces/candidate/candidate?faces-redirect=true&candidateId=" + this.candidate.getId();
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
		}
		catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}