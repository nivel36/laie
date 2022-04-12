package es.nivel36.laie.web.view.client.contact;

import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.client.Contact;
import es.nivel36.laie.ejb.client.ContactService;
import es.nivel36.laie.web.core.view.AbstractView;
import es.nivel36.laie.web.view.client.ViewClientView;

@Named
@ViewScoped
public class AddContactView extends AbstractView {

	private static final long serialVersionUID = 5239278570997577504L;

	private static final Logger logger = LoggerFactory.getLogger(AddContactView.class);

	@Param
	private Client client;

	private Contact contact;

	@Inject
	private transient ContactService contactService;

	@PostConstruct
	public void init() {
		logger.debug("New contact");
		this.contact = new Contact();
		this.contact.setClient(client);
	}

	public void save() {
		logger.debug("Contact add action performed");
		this.contactService.addContact(contact);
		this.navigateTo(ViewClientView.URL + "?client=" + this.client.getId());
	}

	public Contact getContact() {
		return this.contact;
	}

	public void setContact(final Contact contact) {
		this.contact = contact;
	}

	public Client getClient() {
		return client;
	}

	public void setContactService(final ContactService contactService) {
		Objects.requireNonNull(contactService);
		this.contactService = contactService;
	}
}