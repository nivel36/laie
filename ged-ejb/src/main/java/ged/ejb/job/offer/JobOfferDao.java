package ged.ejb.job.offer;

import java.util.List;

import javax.ejb.Local;

import ged.ejb.core.model.Dao;
import ged.ejb.user.User;

@Local
public interface JobOfferDao extends Dao<Long, JobOffer> {

	public List<JobOffer> findByNameAndClient(final String name, final String clientName, final Boolean showDeleted);

	public List<JobOffer> findLastJobOffers(User owner);
}