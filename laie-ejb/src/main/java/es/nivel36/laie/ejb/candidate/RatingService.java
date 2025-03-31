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
		logger.debug("Finding all rating data for ratingId {}", ratingId);
		return this.ratingDao.findAllData(ratingId);
	}

	public Rating findRatingByCandidateAndUser(final Candidate candidate, final User user) {
		Objects.requireNonNull(candidate);
		Objects.requireNonNull(user);
		logger.debug("Finding rating for candidate {} and user {}", candidate, user);
		return this.ratingDao.findRatingOfCandidateByUser(candidate, user);
	}

	public List<Rating> findRatingsByCandidate(final Candidate candidate, final Page page) {
		Objects.requireNonNull(candidate);
		logger.debug("Finding ratings for candidate {}", candidate);
		return this.ratingDao.findRatingsByCandidate(candidate, page);
	}

	public List<Rating> findRatingsByUser(final User user, final Page page) {
		Objects.requireNonNull(user);
		logger.debug("Finding ratings for user {}", user);
		return this.ratingDao.findRatingsByUser(user, page);
	}

	public Rating findRatingById(final long ratingId) {
		logger.debug("Finding rating by id {}", ratingId);
		return this.ratingDao.find(Rating.class, ratingId);
	}

	public void addRating(final Rating rating) {
		Objects.requireNonNull(rating);
		logger.debug("Adding rating {}", rating);
		this.ratingDao.insert(rating);
		updateMeanRating(rating.getCandidate());
	}

	public Rating updateRating(final Rating rating) {
		Objects.requireNonNull(rating);
		logger.debug("Updating rating {}", rating);
		final Rating updatedRating = this.ratingDao.update(rating);
		updateMeanRating(rating.getCandidate());
		return updatedRating;
	}

	public void deleteRating(final Rating rating) {
		Objects.requireNonNull(rating);
		logger.debug("Deleting rating {}", rating);
		this.ratingDao.delete(Rating.class, rating);
		updateMeanRating(rating.getCandidate());
	}

	public void requestRating(final Candidate candidate, final User user) {
		throw new UnsupportedOperationException("Method requestRating is not implemented");
	}

	private void updateMeanRating(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		final List<Rating> ratings = this.ratingDao.findRatingsByCandidate(candidate, Page.ALL_RESULTS);
		final int size = ratings.size();
		if (size == 0) {
			candidate.setRating(0);
		} else {
			int totalScore = 0;
			for (final Rating rating : ratings) {
				totalScore += rating.getScore();
			}
			final float average = (float) totalScore / size;
			candidate.setRating((int) Math.floor(average));
		}
		this.candidateService.updateCandidateRating(candidate);
	}

	public void setRatingDao(final RatingDao ratingDao) {
		this.ratingDao = Objects.requireNonNull(ratingDao);
	}

	public void setCandidateService(final CandidateService candidateService) {
		this.candidateService = Objects.requireNonNull(candidateService);
	}
}
