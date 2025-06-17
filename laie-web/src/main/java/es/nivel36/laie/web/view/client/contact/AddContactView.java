package es.nivel36.laie.web.view.client.contact;

import java.util.Objects;

import org.omnifaces.cdi.Param;
import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.client.Contact;
import es.nivel36.laie.ejb.client.ContactService;
import es.nivel36.laie.web.core.view.AbstractView;
import es.nivel36.laie.web.view.client.ViewClientView;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class AddContactView extends AbstractView {

	private static final long serialVersionUID = 5239278570997577504L;

	private static final Logger logger = LoggerFactory.getLogger(AddContactView.class);

	private @Param Client client;
	private Contact contact;
	private transient @Inject ContactService contactService;

	@PostConstruct
	public void init() {
		logger.debug("New contact");
		this.contact = new Contact();
		this.contact.setClient(client);
	}

	public void save() {
		logger.debug("Contact add action performed");
		this.contactService.addContact(contact);
		Faces.redirect(ViewClientView.getUrl(this.client.getId()));
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
		this.contactService = Objects.requireNonNull(contactService);
	}
}