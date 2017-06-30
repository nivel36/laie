package ged.ejb.client;

import java.util.List;

import ged.ejb.core.model.Dao;

public interface ClientDao extends Dao< Client> {

	Client findByName(final String clientName);

	List<Client> searchByName(final String clientName, final boolean showDeleted);
}