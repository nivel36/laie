package ged.ejb.job.offer;

import java.util.List;

import ged.ejb.core.AuditedService;
import ged.ejb.user.User;

public interface JobOfferService extends AuditedService<JobOffer> {

	List<JobOffer> findAllByOwner(final User owner);

	List<JobOffer> searchByNameAndClient(String name, String clientName, Boolean showDeleted);

	List<JobOffer> findLastJobOffers(User owner);

}