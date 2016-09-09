package ged.ejb.client;

import java.util.List;

import ged.ejb.core.model.Dao;

public interface ClientDao extends Dao<Long, Client> {

	List<Client> searchByName(final String clientName);

	Client findByName(final String clientName);

}
