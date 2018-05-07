package ged.ejb.job.offer;

import java.util.List;

import ged.ejb.candidate.Candidate;
import ged.ejb.client.Client;
import ged.ejb.core.model.Dao;
import ged.ejb.user.User;

public interface JobOfferDao extends Dao<JobOffer> {

	List<JobOffer> findAllByOwner(User owner);

	List<JobOffer> findJobOffersByCandidate(Candidate candidate);

	List<JobOffer> findJobOffersByClient(Client client);

	List<JobOffer> findLastJobOffers(User owner);
}