package ged.ejb.job;

import java.util.List;

import javax.ejb.Local;

import ged.ejb.client.Client;
import ged.ejb.core.model.Dao;
import ged.ejb.user.User;

@Local
public interface JobDao extends Dao<Long, JobOffer> {

	public Client findClientByName(String clientName);

	public List<JobMeeting> findConductedJobMeetingsByJobOffer(JobOffer jobOffer);

	public List<JobOffer> findJobOfferByName(String name);

	public List<JobOffer> findJobOffersByOwner(User owner);

	public List<JobOffer> findLastJobOffers(User owner);

	public List<JobMeeting> findPlannedJobMeetingsByJobOffer(JobOffer jobOffer);

	public List<JobOffer> fullSearch(String matching);

	public List<JobOffer> fullSearchByClientName(String clientName);

	public List<JobOffer> fullSearchByName(String name);

	public List<JobOffer> fullSearchByNameAndClientName(String name, String clientName);

	public Client insertClient(String clientName);

}