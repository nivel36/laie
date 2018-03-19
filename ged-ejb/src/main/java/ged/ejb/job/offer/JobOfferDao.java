package ged.ejb.job.offer;

import java.util.List;

import ged.ejb.core.model.Dao;
import ged.ejb.user.User;

public interface JobOfferDao extends Dao<JobOffer> {

	List<JobOffer> findAllByOwner(final User owner);

	List<JobOffer> findJobOffersByClientId(final long clientId);

	List<JobOffer> findLastJobOffers(final User owner);
}