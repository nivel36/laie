package es.nivel36.laie.web.view.client.contact;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.client.ContactDto;
import es.nivel36.laie.ejb.client.ContactService;
import es.nivel36.laie.web.core.IllegalPageStateException;
import es.nivel36.laie.web.core.util.PageEnum;
import es.nivel36.laie.web.core.view.AbstractView;

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
		this.clientUid = this.getValueFromGetParameters("clientUid");
		if (clientUid == null) {
			throw new IllegalPageStateException();
		}
		this.contact = new ContactDto();
	}

	public String save() {
		logger.debug("Contact add action performed");
		this.contactService.addContact(clientUid, contact);
		return this.navigator.getRedirectUrl(PageEnum.CLIENT, this.clientUid);
	}

	public ContactDto getContact() {
		return this.contact;
	}

	public void setContact(final ContactDto contact) {
		this.contact = contact;
	}

	public void setContactService(final ContactService contactService) {
		this.contactService = contactService;
	}
}