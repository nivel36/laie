package es.nivel36.laie.web.view.client.contact;

import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.client.ContactDto;
import es.nivel36.laie.ejb.client.ContactService;
import es.nivel36.laie.web.core.view.AbstractView;
import es.nivel36.laie.web.view.client.ViewClientView;

@Named
@ViewScoped
public class AddContactView extends AbstractView {

	private static final long serialVersionUID = 5239278570997577504L;

	private static final Logger logger = LoggerFactory.getLogger(AddContactView.class);

	private String clientUid;

	private ContactDto contact;

	@Inject
	private transient ContactService contactService;

	@PostConstruct
	public void init() {
		logger.debug("New contact");
		this.clientUid = this.getValueFromGetParameters("client", true);
		this.contact = new ContactDto();
	}

	public void save() {
		logger.debug("Contact add action performed");
		this.contactService.addContact(clientUid, contact);
		this.navigateTo(ViewClientView.URL + "?client=" + this.clientUid);
	}

	public ContactDto getContact() {
		return this.contact;
	}

	public void setContact(final ContactDto contact) {
		this.contact = contact;
	}

	public String getClientUid() {
		return clientUid;
	}

	public void setContactService(final ContactService contactService) {
		Objects.requireNonNull(contactService);
		this.contactService = contactService;
	}
}