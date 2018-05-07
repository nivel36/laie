package ged.ejb.client;

import java.util.List;

import ged.ejb.core.AuditedService;

public interface ContactService extends AuditedService<Contact> {

	List<Contact> findContactsByClientId(long clientId);

}