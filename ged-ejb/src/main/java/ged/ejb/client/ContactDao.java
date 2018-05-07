package ged.ejb.client;

import java.util.List;

import ged.ejb.core.model.Dao;

public interface ContactDao extends Dao<Contact> {

	List<Contact> findContactsByClientId(long clientId);

}