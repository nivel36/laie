package es.nivel36.laie.web.view.candidate;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.candidate.Rating;
import es.nivel36.laie.ejb.candidate.RatingService;
import es.nivel36.laie.ejb.core.tag.Tag;
import es.nivel36.laie.ejb.user.DuplicateEmailException;
import es.nivel36.laie.web.core.IllegalPageStateException;

@Named
@ViewScoped
public class EditCandidateView extends AbstractCandidateView {

	private static final long serialVersionUID = -5736997599473134307L;

	private static final Logger logger = LoggerFactory.getLogger(EditCandidateView.class);

	private transient @Inject EditCandidatePermission editCandidatePermission;

	private transient @Inject RatingService ratingService;

	private Rating oldRating;

	@PostConstruct
	public void init() {
		if (this.candidate == null) {
			throw new IllegalPageStateException();
		}
		logger.trace("Candidate {} edit init", this.candidate);
		this.checkEditPermissions();
		this.initTags();
		this.candidateImage = this.candidate.getPicture();
		oldRating = this.ratingService.findRatingOfCandidateByUser(candidate, sessionUser.get());
		if (oldRating != null) {
			this.rating = oldRating.getRating();
		}
	}

	private void checkEditPermissions() {
		if (!editCandidatePermission.validate(candidate)) {
			throw new SecurityException();
		}
	}

	private void initTags() {
		if (this.candidate.getTags() == null) {
			this.tags = new ArrayList<>();
		} else {
			this.tags = this.candidate.getTags().stream().map(Tag::getLabel).collect(Collectors.toList());
		}
	}

	public void save() {
		logger.debug("Save candidate action performed");
		try {
			if (this.tags != null) {
				// Los tags pueden ser nulos a pesar de haberse inicializados ya que JSF
				// interpreta colecciones vacias como null
				this.candidate.setTags(this.tags.stream().map(Tag::new).collect(Collectors.toSet()));
			}
			this.saveImage();
			this.candidate = this.candidateService.updateCandidate(this.candidate);
			if (oldRating != null) {
				if (oldRating.getRating().intValue() != this.rating.intValue()) {
					this.oldRating.setRating(rating);
					this.ratingService.updateRating(oldRating);
				}
			} else {
				final Rating newRating = new Rating();
				newRating.setCandidate(candidate);
				newRating.setRating(this.rating);
				newRating.setUser(this.sessionUser.get());
				this.ratingService.addRating(newRating);
			}
			Faces.redirect(this.candidateUrl());
		} catch (final DuplicateEmailException e) {
			this.addErrorToField("candidateForm:email", "candidate.error.email_exists");
		}
	}

	public void setEditCandidatePermission(final EditCandidatePermission editCandidatePermission) {
		Objects.requireNonNull(editCandidatePermission);
		this.editCandidatePermission = editCandidatePermission;
	}
}