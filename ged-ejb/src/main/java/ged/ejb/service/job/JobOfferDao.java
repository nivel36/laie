package ged.ejb.service.job;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import ged.ejb.core.model.GenericDao;
import ged.ejb.service.client.Client;

@Stateless
public class JobOfferDao {

	@Inject
	private GenericDao genericDao;

	public Client findClientByName(final String clientName) {
		Client client;
		try {
			final Map<String, Object> properties = new HashMap<>();
			properties.put("name", clientName);
			client = this.genericDao.getByTypedQuerySingleResult(Client.class, "Client.findByName", properties);
		} catch (final NoResultException ex) {
			client = null;
		}
		return client;
	}

	public void insert(final JobOffer jobOffer) {
		if (jobOffer.getClient() != null) {
			setClientToJobOffer(jobOffer);
		}
		this.genericDao.insert(jobOffer);
	}

	public Client insertClient(final String clientName) {
		final Client client = new Client();
		client.setName(clientName);
		this.genericDao.insert(client);
		return client;
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
