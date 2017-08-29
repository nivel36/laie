package ged.ejb.client;

import java.util.List;

import ged.ejb.core.AuditedService;

public interface ClientService extends AuditedService<Client> {

	boolean clientExist(final String clientName);

	Client findByName(final String clientName);

	List<Client> searchByName(final String clientName);
}
