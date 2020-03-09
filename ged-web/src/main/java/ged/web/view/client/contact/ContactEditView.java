package ged.web.view.client.contact;

import java.lang.invoke.MethodHandles;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.client.Contact;
import ged.ejb.client.ContactService;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class ContactEditView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1L;

	@Inject
	@Param(name = "id", required = true)
	private Contact contact;

	@Inject
	private transient ContactService contactService;

	public Contact getContact() {
		return this.contact;
	}

	public void save() {
		logger.debug("Contact save action performed");
		this.contactService.save(this.contact);
	}

	public void setContact(final Contact contact) {
		this.contact = contact;
	}

	public void setContactService(final ContactService contactService) {
		this.contactService = contactService;
	}
}