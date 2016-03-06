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

	public List<JobMeeting> findPlannedJobMeetingsByJobOffer(JobOffer jobOffer);

	public void insertJobOffer(JobOffer jobOffer);

	public JobOffer updateJobOffer(JobOffer jobOffer);

}