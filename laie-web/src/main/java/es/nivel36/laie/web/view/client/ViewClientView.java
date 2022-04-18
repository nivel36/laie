package es.nivel36.laie.web.view.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.client.ClientService;
import es.nivel36.laie.ejb.client.Contact;
import es.nivel36.laie.ejb.core.bookmark.Bookmark;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.web.core.IllegalPageStateException;
import es.nivel36.laie.web.core.view.AbstractView;

@Named
@ViewScoped
public class ViewClientView extends AbstractView {

	private static final long serialVersionUID = 7741542000560705248L;

	private static final Logger logger = LoggerFactory.getLogger(ViewClientView.class);

	public static final String URL = "/client/view.xhtml";

	private List<Contact> contacts;

	private boolean editable;

	private boolean bookmarkable;

	private Bookmark bookmark;

	private List<JobOffer> jobOffers;

	protected Client client;

	@Inject
	protected transient ClientService clientService;
	
	@PostConstruct
	public void init() {
		initClient();
		logger.trace("Client {} init", this.client);
		this.contacts = new ArrayList<>(this.client.getContacts());
		this.jobOffers = new ArrayList<>(this.client.getJobOffers());
		this.checkDeleted();
		this.editable = true;
		this.bookmark = this.buildBookmark();
		this.bookmarkable = !this.sessionUser.hasBookamrk(bookmark);
	}

	private void initClient() {
		final String clientId = this.getValueFromGetParameters("client", true);
		try {
			final Long id = Long.parseLong(clientId);
			this.client = this.clientService.findAllData(id);
		} catch (NumberFormatException e) {
			logger.warn("Bad number" + clientId);
			throw new IllegalPageStateException();
		}
		if (this.client == null) {
			logger.warn(String.format("Client with id %s not found", clientId));
			throw new IllegalPageStateException();
		}
	}
	
	private void checkDeleted() {
		if (this.client.isDeleted()) {
			logger.warn("Client is deleted");
			this.addMessage(FacesMessage.SEVERITY_WARN, "message.erased_entity", "message.erased_entity");
		}
	}
	
	private Bookmark buildBookmark() {
		final Bookmark bookmark = new Bookmark();
		bookmark.setTitle(client.getName());
		bookmark.setUrl(this.clientUrl());
		bookmark.setUser(sessionUser.get());
		return bookmark;
	}

	public void addBookmark() {
		this.sessionUser.addBookmark(bookmark);
		this.bookmarkable = false;
	}
	
	public void removeFromBookmarks() {
		this.sessionUser.removeFromBookmarks(bookmark);
		this.bookmarkable = true;
	}
	
	public void export() {
		logger.debug("Export client action performed");
	}

	private String clientUrl() {
		return URL + "?client=" + client.getId();
	}
	
	public boolean isBookmarkable() {
		return this.bookmarkable;
	}

	public boolean isEditable() {
		return this.editable;
	}
	
	public Client getClient() {
		return this.client;
	}

	public List<Contact> getContacts() {
		return this.contacts;
	}

	public List<JobOffer> getJobOffers() {
		return this.jobOffers;
	}

	public void setClientService(final ClientService clientService) {
		Objects.requireNonNull(clientService);
		this.clientService = clientService;
	}
}