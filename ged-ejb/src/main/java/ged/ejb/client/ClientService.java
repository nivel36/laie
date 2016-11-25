package ged.ejb.client;

import java.util.List;

import ged.ejb.core.AuditedService;

public interface ClientService extends AuditedService<Long, Client> {

	boolean existsClient(final String clientName);

	Client findByName(final String clientName);

	List<Client> searchByName(final String clientName);
}
