package ged.web.view.client;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.client.Contact;
import ged.ejb.client.ContactService;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class ContactEditBean extends AbstractBean {

	private static final long serialVersionUID = 8611792798437280352L;

	private Contact contact;

	@Inject
	private transient ContactService contactService;

	private String returnPage;

	public String cancel() {
		return this.returnPage;
	}

	public Contact getContact() {
		return this.contact;
	}

	@PostConstruct
	public void init() {
		this.contact = this.getFromFlash(Contact.class, "contact");
		this.returnPage = this.getFromFlash(String.class, "returnPage");
	}

	public String insert() {
		this.contactService.insert(this.contact);
		return this.returnPage;
	}

	public void setContact(final Contact contact) {
		this.contact = contact;
	}

	public void setContactService(final ContactService contactService) {
		this.contactService = contactService;
	}

	public String update() {
		this.contactService.update(this.contact);
		return this.returnPage;
	}

}
