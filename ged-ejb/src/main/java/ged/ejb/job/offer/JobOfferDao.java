package ged.ejb.job.offer;

import java.util.List;

import ged.ejb.client.Client;
import ged.ejb.core.model.Dao;
import ged.ejb.user.User;

public interface JobOfferDao extends Dao<JobOffer> {

	List<JobOffer> findAllByOwner(final User owner);

	List<JobOffer> findAllJobOffersByClient(final Client client);

	List<JobOffer> findLastJobOffers(final User owner);
}