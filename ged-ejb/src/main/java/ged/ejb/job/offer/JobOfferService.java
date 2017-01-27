package ged.ejb.job.offer;

import java.util.List;

import ged.ejb.candidate.Candidate;
import ged.ejb.client.Client;
import ged.ejb.core.AuditedService;
import ged.ejb.user.User;

public interface JobOfferService extends AuditedService<Long, JobOffer> {

	void addJobCandidature(final JobOffer jobOffer, final Candidate candidate);

	List<JobOffer> findAllByClient(final Client client);

	List<JobOffer> findAllByOwner(final User owner);

	List<JobOffer> findLastJobOffers(User owner);

	void removeJobCandidature(final JobOffer jobOffer, final Candidate candidate);

	List<JobOffer> searchByNameAndClient(String name, String clientName, Boolean showDeleted);
}