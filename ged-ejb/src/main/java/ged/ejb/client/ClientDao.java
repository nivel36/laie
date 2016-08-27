package ged.ejb.client;

import ged.ejb.core.model.Dao;

public interface ClientDao extends Dao<Long, Client> {

	Client findByName(final String clientName);

}
