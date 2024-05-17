package es.nivel36.laie.ejb.candidate;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.user.User;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class RatingService {

	private static final Logger logger = LoggerFactory.getLogger(RatingService.class);

	private @Inject RatingDao ratingDao;

	private @Inject CandidateService candidateService;
	
	public Rating findAllRatingData(final long ratingId) {
		Objects.requireNonNull(ratingId);
		logger.debug("Find all rating data of ratingId {}", ratingId);
		return this.ratingDao.findAllData(ratingId);
	}

	public Rating findRatingOfCandidateByUser(final Candidate candidate, final User user) {
		Objects.requireNonNull(candidate);
		Objects.requireNonNull(user);
		logger.debug("Find rating of candidate {} and user {}", candidate, user);
		return this.ratingDao.findRatingOfCandidateByUser(candidate, user);
	}

	public List<Rating> findRatingsByCandidate(final Candidate candidate, final Page page) {
		Objects.requireNonNull(candidate);
		logger.debug("Find rating of candidate {}", candidate);
		return this.ratingDao.findRatingsByCandidate(candidate, page);
	}

	public List<Rating> findRatingsByUser(final User user, final Page page) {
		Objects.requireNonNull(user);
		logger.debug("Find rating of user {}", user);
		return this.ratingDao.findRatingsByUser(user, page);
	}

	public Rating findRatingById(final Long id) {
		Objects.requireNonNull(id);
		logger.debug("Find rating by id {}", id);
		return this.ratingDao.find(Rating.class, id);
	}

	public void addRating(final Rating rating) {
		Objects.requireNonNull(rating);
		logger.debug("Adding rating {}", rating);
		this.ratingDao.insert(rating);
		this.updateMeanRating(rating.getCandidate());
	}

	public Rating updateRating(final Rating rating) {
		Objects.requireNonNull(rating);
		logger.debug("Updating rating {}", rating);
		final Rating updatedRating = ratingDao.update(rating);
		final Candidate candidate = rating.getCandidate();
		this.updateMeanRating(candidate);
		return updatedRating;
	}

	public void deleteRating(final Rating rating) {
		Objects.requireNonNull(rating);
		logger.debug("Deleting rating {}", rating);
		ratingDao.delete(Rating.class, rating);
		this.updateMeanRating(rating.getCandidate());
	}

	public void requestRating(final Candidate candidate, final User user) {

	}

	private void updateMeanRating(final Candidate candidate) {
		final List<Rating> ratings = this.ratingDao.findRatingsByCandidate(candidate, Page.ALL_RESULTS);
		final int size = ratings.size();
		int add = 0;
		for (final Rating rating : ratings) {
			add += rating.getRating();
		}
		float median = add / size;
		candidate.setRating(Integer.valueOf((int) Math.floor(median)));
		this.candidateService.updateCandidateRating(candidate);
	}

	public void setRatingDao(RatingDao ratingDao) {
		this.ratingDao = ratingDao;
	}

	public void setCandidateService(CandidateService candidateService) {
		this.candidateService = candidateService;
	}

}
