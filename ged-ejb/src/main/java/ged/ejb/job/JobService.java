package ged.ejb.job;

import java.util.List;

import javax.ejb.Local;

import ged.ejb.core.AuditedService;
import ged.ejb.user.User;

@Local
public interface JobService extends AuditedService<JobOffer> {

	public List<JobMeeting> findConductedJobMeetingsByJobOffer(JobOffer jobOffer);

	public List<JobOffer> findJobOfferByName(String name);

	public List<JobOffer> findJobOffersByOwner(User owner);

	public List<JobOffer> findLastJobOffers(User owner);

	public List<JobMeeting> findPlannedJobMeetingsByJobOffer(JobOffer jobOffer);

	public List<JobOffer> fullSearch(String matching);

	public List<JobOffer> fullSearchByClientName(String clientName);

	public List<JobOffer> fullSearchByName(String name);

	public List<JobOffer> fullSearchByNameAndClientName(String name, String clientName);

}