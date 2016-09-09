package ged.ejb.client;

import java.util.List;

import ged.ejb.core.Service;

public interface ClientService extends Service<Long, Client> {

	List<Client> searchByName(final String clientName);

	Client findByName(final String clientName);
}
