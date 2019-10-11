package ged.web.view.client;

import java.lang.invoke.MethodHandles;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.client.Client;
import ged.ejb.client.Contact;
import ged.ejb.core.model.Address;

@Named
@ViewScoped
public class AddClientView extends AbstractClientView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1L;

	private Contact contact;

	public void addContact() {
		this.client.addContact(this.contact);
		this.contact = buildNewContact();
	}

	private Client buildNewClient() {
		final Client newClient = new Client();
		newClient.setOwner(this.sessionUser.get());
		newClient.setAddress(new Address());
		return newClient;
	}

	public Contact getContact() {
		return this.contact;
	}

	@PostConstruct
	public void init() {
		logger.trace("New client init");
		this.client = this.buildNewClient();
		this.contact = this.buildNewContact();
	}
	
	public Contact buildNewContact() {
		return new Contact();
	}
	
	public void removeContact(Contact contact) {
		this.client.deleteContact(contact);
	}
	
	public void editContact(Contact contact) {
		this.contact = contact;
	}

	public String save() {
		logger.debug("Create new client action performed");
		this.client = this.clientService.save(this.client);
		return this.clientUrl();
	}

	public void setContact(final Contact contact) {
		this.contact = contact;
	}
}