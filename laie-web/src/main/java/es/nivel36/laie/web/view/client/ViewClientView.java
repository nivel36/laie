package es.nivel36.laie.web.view.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.omnifaces.cdi.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.client.Contact;
import es.nivel36.laie.ejb.core.bookmark.Bookmark;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.job.offer.JobOfferService;
import es.nivel36.laie.web.core.IllegalPageStateException;
import es.nivel36.laie.web.core.view.AbstractView;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class ViewClientView extends AbstractView {

	private static final long serialVersionUID = -3020432572054873614L;

	private static final Logger logger = LoggerFactory.getLogger(ViewClientView.class);

	private static final String URL = "/client/view.xhtml";

	private List<Contact> contacts;

	private boolean editable;

	private boolean bookmarkable;

	private Bookmark bookmark;

	private boolean addJobOffer;

	private List<JobOffer> jobOffers;

	private @Param Client client;

	private transient @Inject JobOfferService jobOfferService;

	private transient @Inject EditClientPermission editClientPermission;

	@PostConstruct
	public void init() {
		if (this.client == null) {
			logger.warn("Client not found");
			throw new IllegalPageStateException();
		}
		logger.trace("Client {} init", this.client);
		this.contacts = new ArrayList<>(this.client.getContacts());
		this.jobOffers = jobOfferService.findJobOffersByClient(client, Page.ALL_RESULTS);
		this.checkDeleted();
		this.editable = editClientPermission.validate(client);
		this.addJobOffer = editable;
		this.bookmark = this.buildBookmark();
		this.bookmarkable = !this.sessionUser.hasBookamrk(bookmark);
	}

	private void checkDeleted() {
		if (this.client.isDeleted()) {
			logger.warn("Client is deleted");
			this.addMessage(FacesMessage.SEVERITY_WARN, "message.erased_entity", "message.erased_entity");
		}
	}

	public static String getUrl(long clientId) {
		return URL + "?client=" + clientId;
	}

	private Bookmark buildBookmark() {
		final Bookmark bookmark = new Bookmark();
		bookmark.setTitle(client.getName());
		bookmark.setUrl(this.clientUrl());
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

	public boolean isAddJobOffer() {
		return addJobOffer;
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

	public void setJobOfferService(final JobOfferService jobOfferService) {
		Objects.requireNonNull(jobOfferService);
		this.jobOfferService = jobOfferService;
	}
}