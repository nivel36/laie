package ged.web.view.client;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.client.Client;
import ged.ejb.client.ClientService;
import ged.ejb.client.Contact;
import ged.ejb.client.ContactService;
import ged.web.core.PageNotFoundException;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class ContactPanelBean extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = -2270817798985551387L;

	private Client client;

	@Inject
	private transient ClientService clientService;

	private List<Contact> contacts;

	@Inject
	private transient ContactService contactService;

	public Client getClient() {
		return this.client;
	}

	public List<Contact> getContacts() {
		return this.contacts;
	}

	@PostConstruct
	public void init() {
		logger.trace("Init ContactPanelBean");
		final String clientIdValue = this.getValueFromGetParameters("clientId");
		if (clientIdValue == null) {
			logger.error("ClientId is null");
			throw new PageNotFoundException();
		}
		final Long clientId = Long.parseLong(clientIdValue);
		this.client = this.clientService.find(clientId);
		this.contacts = this.contactService.findContactsByClient(this.client);
	}

	public void setContacts(final List<Contact> contacts) {
		this.contacts = contacts;
	}

	public void setContactService(final ContactService contactService) {
		this.contactService = contactService;
	}
}