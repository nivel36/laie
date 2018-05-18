package ged.ejb.client;

import ged.ejb.core.AuditedService;

public interface ClientService extends AuditedService<Client> {

	boolean clientExists(String clientName);

	Client findAllClientDataByClientId(long clientId);

	Client findClientByName(String clientName);
}