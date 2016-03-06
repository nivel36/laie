package ged.ejb.job.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import ged.ejb.client.Client;
import ged.ejb.core.Repository;
import ged.ejb.core.model.PersistenceFacade;
import ged.ejb.job.JobMeeting;
import ged.ejb.job.JobOffer;
import ged.ejb.job.JobOfferDao;

@Repository
public class JobOfferDaoImpl implements JobOfferDao {

	@Inject
	@Repository
	private PersistenceFacade persistenceFacade;

	@Override
	public void deleteJobOffer(final JobOffer jobOffer) {
		this.persistenceFacade.delete(jobOffer);
	}

	@Override
	public List<JobOffer> findAllJobOffers() {
		return this.persistenceFacade.getAll(JobOffer.class);
	}

	@Override
	public Client findClientByName(final String clientName) {
		Client client;
		try {
			final Map<String, Object> properties = new HashMap<>();
			properties.put("name", clientName);
			client = this.persistenceFacade.getByTypedQuerySingleResult(Client.class, "Client.findByName", properties);
		} catch (final NoResultException ex) {
			client = null;
		}
		return client;
	}

	@Override
	public List<JobMeeting> findConductedJobMeetingsByJobOffer(final JobOffer jobOffer) {
		List<JobMeeting> conductedJobMeetings = null;
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("jobOffer", jobOffer);
		conductedJobMeetings = this.persistenceFacade.getByTypedQuery(JobMeeting.class,
				"JobMeeting.getConductedJobMeetings", parameters, 0, 0);
		return conductedJobMeetings;
	}

	@Override
	public JobOffer findJobOfferById(final long id) {
		return this.persistenceFacade.getByPrimaryKey(JobOffer.class, id);
	}

	@Override
	public List<JobOffer> findJobOfferByName(final String name) {
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("name", name);
		return this.persistenceFacade.getByTypedQuery(JobOffer.class, "JobOffer.findByName", parameters, 0, 0);
	}

	@Override
	public List<JobMeeting> findPlannedJobMeetingsByJobOffer(final JobOffer jobOffer) {
		List<JobMeeting> plannedJobMeetings = null;
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("jobOffer", jobOffer);
		plannedJobMeetings = this.persistenceFacade.getByTypedQuery(JobMeeting.class,
				"JobMeeting.getPlannedJobMeetings", parameters, 0, 0);
		return plannedJobMeetings;
	}

	@Override
	public Client insertClient(final String clientName) {
		final Client client = new Client();
		client.setName(clientName);
		this.persistenceFacade.insert(client);
		return client;
	}

	@Override
	public void insertJobOffer(final JobOffer jobOffer) {
		if (jobOffer.getClient() != null) {
			setClientToJobOffer(jobOffer);
		}
		this.persistenceFacade.insert(jobOffer);
	}

	private void setClientToJobOffer(final JobOffer jobOffer) {
		final String clientName = jobOffer.getClient().getName();
		Client client = findClientByName(clientName);
		if (client == null) {
			client = insertClient(clientName);
		}
		jobOffer.setClient(client);
	}

	@Override
	public JobOffer updateJobOffer(final JobOffer jobOffer) {
		return this.persistenceFacade.update(jobOffer);
	}
}
