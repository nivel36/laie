package ged.web.view.client;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Ajax;
import org.primefaces.PrimeFaces;

import ged.ejb.client.Client;
import ged.ejb.client.Contact;
import ged.ejb.client.ContactService;
import ged.ejb.core.util.Parameters;
import ged.web.core.util.Navigate;
import ged.web.core.util.Page;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class ContactEditBean extends AbstractBean {

	private static final long serialVersionUID = 8611792798437280352L;

	private Contact contact;

	@Inject
	private transient ContactService contactService;

	private void buildContact(final Client client) {
		this.contact = new Contact();
		this.contact.setClient(client);
		this.contact.setPhoneNumber(client.getPhoneNumber());
	}

	public void clear() {
		this.contact = null;
	}

	public Contact getContact() {
		return this.contact;
	}

	public void openDialog(final Client client) {
		buildContact(client);
		PrimeFaces.current().executeScript("PF('newContactDlg').show()");
		Ajax.update("newContactForm");
	}

	public void save() {
		this.contactService.insert(this.contact);
		Navigate.to(Page.CLIENT).withParams(Parameters.map("id", this.contact.getClient().getId())).doGet();
	}

	public void setContact(final Contact contact) {
		this.contact = contact;
	}

	public void setContactService(final ContactService contactService) {
		this.contactService = contactService;
	}
}