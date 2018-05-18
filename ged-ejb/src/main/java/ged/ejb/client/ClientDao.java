package ged.ejb.client;

import ged.ejb.core.model.Dao;

public interface ClientDao extends Dao<Client> {

	boolean clientExists(final String clientName);

	Client findAllClientDataByClientId(long clientId);

	Client findClientByName(final String clientName);

}