package ged.ejb.job.offer;

import java.util.List;

import ged.ejb.candidate.Candidate;
import ged.ejb.client.Client;
import ged.ejb.core.AuditedService;
import ged.ejb.job.meeting.JobMeeting;
import ged.ejb.user.User;

public interface JobOfferService extends AuditedService<JobOffer> {

	void addJobCandidature(JobOffer jobOffer, Candidate candidate);

	void addJobCandidatures(JobOffer jobOffer, List<Candidate> candidates);

	void addJobMeeting(JobMeeting jobMeeting);

	List<JobOffer> findAllJobOffersByOwner(User owner);

	List<JobOffer> findJobOffersByCandidate(Candidate candidate);

	List<JobOffer> findJobOffersByClient(Client client);

	List<JobOffer> findLastJobOffers(User owner);

	void removeJobCandidature(JobOffer jobOffer, Candidate candidate);
}