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

import es.nivel36.laie.ejb.client.ClientDto;
import es.nivel36.laie.ejb.client.ContactDto;
import es.nivel36.laie.ejb.core.bookmark.BookmarkDto;
import es.nivel36.laie.ejb.core.bookmark.BookmarkService;
import es.nivel36.laie.ejb.core.model.AddressDto;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.job.offer.JobOfferDto;
import es.nivel36.laie.ejb.job.offer.JobOfferService;
import es.nivel36.laie.web.core.IllegalPageStateException;
import es.nivel36.laie.web.core.util.PageEnum;

@Named
@ViewScoped
public class ViewClientView extends AbstractClientView {

	private static final long serialVersionUID = 7741542000560705248L;

	private static final Logger logger = LoggerFactory.getLogger(ViewClientView.class);

	private List<ContactDto> contacts;

	private boolean editable;

	private List<JobOfferDto> jobOffers;

	@Inject
	private transient JobOfferService jobOfferService;

	@Inject
	private transient BookmarkService bookmarkService;

	@PostConstruct
	public void init() {
		String uid = this.getValueFromGetParameters("client", true);
		this.client = this.clientService.findClientByUid(uid);
		if (this.client == null) {
			throw new IllegalPageStateException();
		}
		logger.trace("Client {} init", this.client);
		if (this.client.getAddress() == null) {
			this.client.setAddress(new AddressDto());
		}
		this.contacts = new ArrayList<>(this.client.getContacts());
		this.jobOffers = this.jobOfferService.findJobOffersByOwner(this.client.getUid(), Page.ALL_RESULTS);
		this.checkDeleted();
		this.editable = true;
	}

	private void checkDeleted() {
		if (this.client.isDeleted()) {
			logger.warn("Client is deleted");
			this.addMessage(FacesMessage.SEVERITY_WARN, "message.erased_entity", "message.erased_entity");
		}
	}

	public String editClient() {
		logger.debug("Edit client action performed");
		return this.navigator.getRedirectUrl(PageEnum.CLIENT_EDIT, this.client.getUid());
	}

	public void addToBoorkmarks() {
		final BookmarkDto bookmark = new BookmarkDto();
		bookmark.setTitle(client.getName());
		bookmark.setUrl("/client/view?client=" + this.client.getUid());
		this.bookmarkService.addBookmark(bookmark, this.sessionUser.get().getUid());
	}

	public void export() {
		logger.debug("Export client action performed");
	}

	public List<ContactDto> getContacts() {
		return this.contacts;
	}

	public List<JobOfferDto> getJobOffers() {
		return this.jobOffers;
	}

	public boolean isEditable() {
		return this.editable;
	}

	public void setClient(final ClientDto client) {
		this.client = client;
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		Objects.requireNonNull(jobOfferService);
		this.jobOfferService = jobOfferService;
	}
	
	public void setBookmarkService(final BookmarkService bookmarkService) {
		Objects.requireNonNull(bookmarkService);
		this.bookmarkService = bookmarkService;
	}
}