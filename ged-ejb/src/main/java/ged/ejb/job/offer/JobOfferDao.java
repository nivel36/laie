package ged.ejb.job.offer;

import java.util.List;

import ged.ejb.core.model.Dao;
import ged.ejb.user.User;

public interface JobOfferDao extends Dao<Long, JobOffer> {

	List<JobOffer> findAllByOwner(final User owner);

	List<JobOffer> searchByNameAndClient(final String name, final String clientName, final Boolean showDeleted);

	List<JobOffer> findLastJobOffers(final User owner);

}