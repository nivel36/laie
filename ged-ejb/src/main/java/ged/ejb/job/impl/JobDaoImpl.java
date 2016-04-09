package ged.ejb.job.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import ged.ejb.client.Client;
import ged.ejb.core.Repository;
import ged.ejb.core.model.PersistenceFacade;
import ged.ejb.job.JobDao;
import ged.ejb.job.JobMeeting;
import ged.ejb.job.JobOffer;
import ged.ejb.user.User;

@Repository
public class JobDaoImpl implements JobDao {

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
	public List<JobOffer> findLastJobOffers(final User owner) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("owner", owner);
		return this.persistenceFacade.getByTypedQuery(JobOffer.class, "JobOffer.findLastJobOffers", parameters, 10, 0);
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
	public List<JobOffer> fullSearch(final String matching) {
		return this.persistenceFacade.fullSearch(JobOffer.class, matching, "name", "client.name", "description",
				"city");
	}

	@Override
	public List<JobOffer> fullSearchByClientName(final String clientName) {
		return this.persistenceFacade.fullSearch(JobOffer.class, clientName, "client.name");
	}

	@Override
	public List<JobOffer> fullSearchByName(final String name) {
		return this.persistenceFacade.fullSearch(JobOffer.class, name, "name");
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

	@Override
	public JobOffer updateJobOffer(final JobOffer jobOffer) {
		return this.persistenceFacade.update(jobOffer);
	}
}
