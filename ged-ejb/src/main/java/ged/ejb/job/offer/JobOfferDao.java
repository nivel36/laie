package ged.ejb.job.offer;

import java.util.List;

import ged.ejb.core.model.Dao;
import ged.ejb.user.User;

public interface JobOfferDao extends Dao<JobOffer> {

	List<JobOffer> findAllByOwner(User owner);

	List<JobOffer> findJobOffersByCandidateId(long candidateId);

	List<JobOffer> findJobOffersByClientId(long clientId);

	List<JobOffer> findLastJobOffers(User owner);
}