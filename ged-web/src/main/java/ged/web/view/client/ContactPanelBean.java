package ged.web.view.client;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;

import ged.ejb.client.Contact;
import ged.ejb.client.ContactService;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class ContactPanelBean extends AbstractBean {

	private static final long serialVersionUID = -2270817798985551387L;

	@Inject
	@Param
	private Long clientId;

	private List<Contact> contacts;

	@Inject
	private ContactService contactService;

	public Long getClientId() {
		return this.clientId;
	}

	public List<Contact> getContacts() {
		return this.contacts;
	}

	@PostConstruct
	public void init() {
		this.contacts = this.contactService.findByClientId(this.clientId);
	}

	public void setClientId(final Long clientId) {
		this.clientId = clientId;
	}

	public void setContacts(final List<Contact> contacts) {
		this.contacts = contacts;
	}

	public void setContactService(final ContactService contactService) {
		this.contactService = contactService;
	}
}