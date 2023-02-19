package es.nivel36.laie.web.view.candidate;

import java.util.Objects;

import org.omnifaces.cdi.Param;
import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.candidate.Rating;
import es.nivel36.laie.ejb.candidate.RatingService;
import es.nivel36.laie.web.core.view.AbstractView;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class EditRatingView extends AbstractView {

	private static final long serialVersionUID = -3381940613398430712L;

	private static final Logger logger = LoggerFactory.getLogger(EditRatingView.class);

	private @Param(required = true) Rating rating;
	
	private @Param(required = true) Candidate candidate;

	private transient @Inject RatingService ratingService;

	public void save() {
		logger.debug("Update rating ACTION performed");
		rating.setCandidate(candidate);
		this.rating = ratingService.updateRating(rating);
		final long candidateId = rating.getCandidate().getId();
		final String candidateUrl = ViewCandidateView.getUrl(candidateId);
		Faces.redirect(candidateUrl);
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public void setRatingService(RatingService ratingService) {
		Objects.requireNonNull(ratingService);
		this.ratingService = ratingService;
	}
}
