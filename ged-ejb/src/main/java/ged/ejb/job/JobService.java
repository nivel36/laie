package ged.ejb.job;

import java.util.List;

import javax.ejb.Local;

@Local
public interface JobService {

	public void deleteJobOffer(JobOffer jobOffer);

	public List<JobOffer> findAllJobOffers();

	public List<JobMeeting> findConductedJobMeetingsByJobOffer(JobOffer jobOffer);

	public JobOffer findJobOfferById(long id);

	public List<JobOffer> findJobOfferByName(String name);

	public List<JobOffer> findLastJobOffers();

	public List<JobMeeting> findPlannedJobMeetingsByJobOffer(JobOffer jobOffer);

	public List<JobOffer> fullSearch(String matching);

	public List<JobOffer> fullSearchByClientName(String clientName);

	public List<JobOffer> fullSearchByName(String name);

	public List<JobOffer> fullSearchByNameAndClientName(String name, String clientName);

	public void insertJobOffer(JobOffer jobOffer);

	public JobOffer updateJobOffer(JobOffer jobOffer);

}