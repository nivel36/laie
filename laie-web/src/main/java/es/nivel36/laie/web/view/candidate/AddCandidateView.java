package es.nivel36.laie.web.view.candidate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.candidate.Rating;
import es.nivel36.laie.ejb.core.tag.Tag;
import es.nivel36.laie.ejb.user.DuplicateEmailException;

@Named
@ViewScoped
public class AddCandidateView extends AbstractCandidateView {

	private static final long serialVersionUID = -3346845253673407579L;

	private static final Logger logger = LoggerFactory.getLogger(AddCandidateView.class);

	private transient @Inject AddCandidatePermission addCandidatePermission;

	private Integer rating;

	@PostConstruct
	public void init() {
		logger.trace("New candidate init");
		this.checkAddPermissions();
		this.candidate = new Candidate();
		this.candidate.setOwner(sessionUser.get());
		this.tags = new ArrayList<>();
	}

	private void checkAddPermissions() {
		if (!addCandidatePermission.validate(null)) {
			throw new SecurityException();
		}
	}

	public void save() {
		logger.debug("Create new candidate action performed");
		try {
			if (this.tags != null) {
				// Los tags pueden ser nulos a pesar de haberse inicializados ya que JSF
				// interpreta colecciones vacias como null
				this.candidate.setTags(this.tags.stream().map(Tag::new).collect(Collectors.toSet()));
			}
			candidate.setRatings(new HashSet<Rating>());
			if (this.rating != null && this.rating != 0) {
				final Rating rating = new Rating();
				rating.setCandidate(candidate);
				rating.setUser(this.sessionUser.get());
				rating.setRating(this.rating);
				this.candidate.getRatings().add(rating);
				this.candidate.setRating(this.rating);
			}
			this.candidateService.addCandidate(candidate);
			this.saveImage();
			Faces.redirect(this.candidateUrl());
		} catch (final DuplicateEmailException e) {
			this.addErrorToField("candidateForm:email", "candidate.error.email_exists");
		}
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public void setAddCandidatePermission(final AddCandidatePermission addCandidatePermission) {
		Objects.requireNonNull(addCandidatePermission);
		this.addCandidatePermission = addCandidatePermission;
	}
}
