package ged.ejb.job.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import ged.ejb.client.Client;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.PersistenceFacade;
import ged.ejb.core.model.Repository;
import ged.ejb.job.JobDao;
import ged.ejb.job.JobMeeting;
import ged.ejb.job.JobOffer;
import ged.ejb.user.User;

@Repository
public class JobDaoImpl extends AbstractDao<Long, JobOffer> implements JobDao {

	@Inject
	@Repository
	private PersistenceFacade persistenceFacade;

	@Override
	public Client findClientByName(final String clientName) {
		Client client;
		try {
			final Map<String, Object> properties = new HashMap<>();
			properties.put("name", clientName);
			client = this.persistenceFacade.findByTypedQuery(Client.class, "Client.findByName", properties);
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
		conductedJobMeetings = this.persistenceFacade.findByTypedQuery(JobMeeting.class,
				"JobMeeting.getConductedJobMeetings", parameters, 0, 0);
		return conductedJobMeetings;
	}

	@Override
	public List<JobOffer> findJobOfferByName(final String name) {
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("name", name);
		return this.persistenceFacade.findByTypedQuery(JobOffer.class, "JobOffer.findByName", parameters, 0, 0);
	}

	@Override
	public List<JobOffer> findJobOffersByOwner(final User owner) {
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("owner", owner);
		return this.persistenceFacade.findByTypedQuery(JobOffer.class, "JobOffer.findByOwner", parameters, 0, 0);
	}

	@Override
	public List<JobOffer> findLastJobOffers(final User owner) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("owner", owner);
		return this.persistenceFacade.findByTypedQuery(JobOffer.class, "JobOffer.findLastJobOffers", parameters, 10, 0);
	}

	@Override
	public List<JobMeeting> findPlannedJobMeetingsByJobOffer(final JobOffer jobOffer) {
		List<JobMeeting> plannedJobMeetings = null;
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("jobOffer", jobOffer);
		plannedJobMeetings = this.persistenceFacade.findByTypedQuery(JobMeeting.class,
				"JobMeeting.getPlannedJobMeetings", parameters, 0, 0);
		return plannedJobMeetings;
	}

	@Override
	public List<JobOffer> fullSearch(final String matching) {
		// TODO: add full search
		return null;
	}

	@Override
	public List<JobOffer> fullSearchByClientName(final String clientName) {
		// TODO: add full search
		return null;
	}

	@Override
	public List<JobOffer> fullSearchByName(final String name) {
		// TODO: add full search
		return null;
	}

	@Override
	public List<JobOffer> fullSearchByNameAndClientName(final String name, final String clientName) {
		List<JobOffer> results = fullSearchByName(name);
		if ((results != null) && (results.size() != 0)) {
			results.addAll(fullSearchByClientName(clientName));
			results = removeDuplicated(results);
		} else {
			results = fullSearchByClientName(clientName);
		}
		return results;
	}

	@Override
	public Class<JobOffer> getClazz() {
		return JobOffer.class;
	}

	@Override
	public void insert(final JobOffer jobOffer) {
		if (jobOffer.getClient() != null) {
			setClientToJobOffer(jobOffer);
		}
		this.persistenceFacade.insert(jobOffer);
	}

	@Override
	public Client insertClient(final String clientName) {
		final Client client = new Client();
		client.setName(clientName);
		this.persistenceFacade.insert(client);
		return client;
	}

	private void orderList(final List<JobOffer> results) {
		Collections.sort(results, (o1, o2) -> {
			final JobOffer jo1 = o1;
			final JobOffer jo2 = o2;
			return String.CASE_INSENSITIVE_ORDER.compare(jo1.getName(), jo2.getName());
		});
	}

	private List<JobOffer> removeDuplicated(final List<JobOffer> jobOffers) {
		orderList(jobOffers);
		final List<JobOffer> copy = new ArrayList<JobOffer>(jobOffers);
		JobOffer previousUser = null;
		for (final JobOffer jobOffer : jobOffers) {
			if (jobOffer.equals(previousUser)) {
				copy.remove(jobOffer);
			}
			previousUser = jobOffer;
		}
		return copy;
	}

	private void setClientToJobOffer(final JobOffer jobOffer) {
		final String clientName = jobOffer.getClient().getName();
		Client client = findClientByName(clientName);
		if (client == null) {
			client = insertClient(clientName);
		}
		jobOffer.setClient(client);
	}
}
