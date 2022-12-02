package es.nivel36.laie.web.view.candidate;

import java.util.Objects;

import javax.faces.convert.FacesConverter;

import es.nivel36.laie.ejb.candidate.Rating;
import es.nivel36.laie.ejb.candidate.RatingService;
import es.nivel36.laie.web.core.AbstractConverter;
import jakarta.inject.Inject;

@FacesConverter(managed = true, forClass = Rating.class)
public class RatingConveter extends AbstractConverter<Rating> {

	private @Inject RatingService ratingService;

	@Override
	protected Rating getAsObject(Long id) {
		Objects.requireNonNull(id);
		return ratingService.findRatingById(id);
	}

	public void setRatingService(final RatingService ratingService) {
		Objects.requireNonNull(ratingService);
		this.ratingService = ratingService;
	}
}