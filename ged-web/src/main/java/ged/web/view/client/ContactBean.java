package ged.web.view.client;

import ged.web.core.view.AbstractBean;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ged.ejb.client.Contact;

@Named
@ViewScoped
public class ContactBean extends AbstractBean {

	private static final long serialVersionUID = 8611792798437280352L;

	private Contact contact;

	public Contact getContact() {
		return this.contact;
	}

	@PostConstruct
	public void init() {
		if (this.flash.containsKey("contact")) {
			this.contact = (Contact) this.flash.get("contact");
		}
	}

	public void setContact(final Contact contact) {
		this.contact = contact;
	}

}
