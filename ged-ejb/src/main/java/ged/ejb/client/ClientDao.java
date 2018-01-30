package ged.ejb.client;

import ged.ejb.core.model.Dao;

public interface ClientDao extends Dao<Client> {

	boolean clientExist(final String clientName);

	Client findByName(final String clientName);

}