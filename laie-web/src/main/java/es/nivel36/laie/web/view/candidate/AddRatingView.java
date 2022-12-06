package es.nivel36.laie.web.view.candidate;

import java.util.Objects;

import org.omnifaces.cdi.Param;
import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.candidate.Rating;
import es.nivel36.laie.ejb.candidate.RatingService;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.web.core.view.AbstractView;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class AddRatingView extends AbstractView {

	private static final long serialVersionUID = -2677678536917974288L;

	private static final Logger logger = LoggerFactory.getLogger(AddRatingView.class);

	private transient @Inject RatingService ratingService;

	private @Param(required = true) Candidate candidate;

	private Rating rating;

	@PostConstruct
	public void init() {
		final User user = this.sessionUser.get();
		logger.trace("Add rating for candidate {} and user {} init", this.candidate, user);
		rating = new Rating();
		rating.setCandidate(candidate);
		rating.setUser(user);
	}

	public void save() {
		logger.debug("Add rating ACTION performed");
		this.ratingService.addRating(rating);
		Faces.redirect(ViewCandidateView.getUrl(candidate.getId()));
	}
	
	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public void setRatingService(final RatingService ratingService) {
		Objects.requireNonNull(ratingService);
		this.ratingService = ratingService;
	}
}
