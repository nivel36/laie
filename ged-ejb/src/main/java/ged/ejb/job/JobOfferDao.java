package ged.ejb.job;

import java.util.List;

import javax.ejb.Local;

import ged.ejb.client.Client;

@Local
public interface JobOfferDao {

	public void deleteJobOffer(JobOffer jobOffer);

	public List<JobOffer> findAllJobOffers();

	public Client findClientByName(String clientName);

	public List<JobMeeting> findConductedJobMeetingsByJobOffer(JobOffer jobOffer);

	public JobOffer findJobOfferById(long id);

	public List<JobOffer> findJobOfferByName(String name);

	public List<JobMeeting> findPlannedJobMeetingsByJobOffer(JobOffer jobOffer);

	public Client insertClient(String clientName);

	public void insertJobOffer(JobOffer jobOffer);

	public JobOffer updateJobOffer(JobOffer jobOffer);

}