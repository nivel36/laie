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
public class EditContactView extends AbstractView {

	private static final long serialVersionUID = 158765276093072012L;

	private static final Logger logger = LoggerFactory.getLogger(EditContactView.class);

	private ContactDto contact;

	private String uid;

	private String clientUid;

	@Inject
	private transient ContactService contactService;

	@PostConstruct
	public void init() {
		this.uid = this.getValueFromGetParameters("uid", true);
		this.clientUid = this.getValueFromGetParameters("clientUid", true);
		this.contact = this.contactService.findContactByUid(uid);
		if (this.contact == null) {
			throw new IllegalPageStateException();
		}
	}

	public String save() {
		logger.debug("Contact save action performed");
		this.contactService.updateContact(this.contact);
		return this.navigator.getRedirectUrl(PageEnum.CLIENT, this.clientUid);
	}
	
	public String delete() {
		logger.debug("Contact delete action performed");
		this.contactService.deleteContact(this.uid);
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