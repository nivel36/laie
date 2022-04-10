package es.nivel36.laie.web.view.client.contact;

import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.client.Contact;
import es.nivel36.laie.ejb.client.ContactService;
import es.nivel36.laie.web.core.IllegalPageStateException;
import es.nivel36.laie.web.core.view.AbstractView;
import es.nivel36.laie.web.view.client.ViewClientView;

@Named
@ViewScoped
public class EditContactView extends AbstractView {

	private static final long serialVersionUID = 158765276093072012L;

	private static final Logger logger = LoggerFactory.getLogger(EditContactView.class);

	@Param(name = "contact", converter = "contactConverter")
	private Contact contact;

	@Inject
	private transient ContactService contactService;

	@PostConstruct
	public void init() {
		if (this.contact == null) {
			throw new IllegalPageStateException();
		}
	}

	public void save() {
		logger.debug("Contact save action performed");
		this.contactService.updateContact(this.contact);
		this.navigateTo(ViewClientView.URL + "?client=" + this.contact.getClient().getId());
	}

	public void delete() {
		logger.debug("Contact delete action performed");
		final Long id = this.contact.getClient().getId();
		this.contactService.deleteContact(this.contact);
		this.navigateTo(ViewClientView.URL + "?client=" + id);
	}

	public Contact getContact() {
		return this.contact;
	}

	public void setContact(final Contact contact) {
		this.contact = contact;
	}

	public void setContactService(final ContactService contactService) {
		Objects.requireNonNull(contactService);
		this.contactService = contactService;
	}
}