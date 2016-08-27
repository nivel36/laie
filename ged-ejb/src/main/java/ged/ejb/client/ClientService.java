package ged.ejb.client;

import ged.ejb.core.Service;

public interface ClientService extends Service<Long, Client> {

	Client findByName(final String clientName);
}
