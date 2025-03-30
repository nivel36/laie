package es.nivel36.laie.web.view.client;

import java.util.Map;
import java.util.Objects;

import org.omnifaces.cdi.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.client.ClientService;
import es.nivel36.laie.ejb.core.bookmark.Bookmark;
import es.nivel36.laie.web.core.IllegalPageStateException;
import es.nivel36.laie.web.core.view.AbstractView;
import es.nivel36.laie.web.view.client.contact.ContactLazyDataModel;
import es.nivel36.laie.web.view.job.JobOfferByClientLazyDataModel;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class ViewClientView extends AbstractView {

	private static final long serialVersionUID = -3020432572054873614L;

	private static final Logger logger = LoggerFactory.getLogger(ViewClientView.class);

	private static final String URL = "/client/view.xhtml";

	private boolean editable;

	private boolean bookmarkable;

	private Bookmark bookmark;

	private boolean addJobOffer;

	private Client client;

	private @Param(required = true, name = "client") String clientId;

	private transient @Inject ClientService clientService;

	private @Inject JobOfferByClientLazyDataModel jobOffers;

	private @Inject ContactLazyDataModel contacts;

	private transient @Inject EditClientPermission editClientPermission;

	private int columns;

	public int getColumns() {
		return columns;
	}

	public void updateColumns() {
	    FacesContext context = FacesContext.getCurrentInstance();
	    Map<String, String> params = context.getExternalContext().getRequestParameterMap();
	    String columnsParam = params.get("columns");
	    int columns = Integer.parseInt(columnsParam);

	    // Asumiendo que tienes una propiedad `columns` para manejar esto
	    this.columns = columns;

	    // Actualiza el modelo de datos si es necesario
	}

	@PostConstruct
	public void init() {
		logger.trace("Client {} init", this.clientId);
		this.findClient();
		this.checkDeleted();
		this.editable = editClientPermission.validate(client);
		this.addJobOffer = editable;
		this.bookmark = this.buildBookmark();
		this.bookmarkable = !this.sessionUser.hasBookamrk(bookmark);

		this.jobOffers.setClient(client);
		this.contacts.setClient(client);
	}

	private void findClient() {
		try {
			final Long id = Long.parseLong(clientId);
			this.client = this.clientService.findClientById(id.longValue());
			if (this.client == null) {
				throw new IllegalPageStateException();
			}
		} catch (final NumberFormatException ex) {
			throw new IllegalPageStateException();
		}
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

	public ContactLazyDataModel getContacts() {
		return this.contacts;
	}

	public JobOfferByClientLazyDataModel getJobOffers() {
		return this.jobOffers;
	}

	public void setClientService(final ClientService clientService) {
		Objects.requireNonNull(clientService);
		this.clientService = clientService;
	}

	public void setEditClientPermission (final EditClientPermission editClientPermission) {
		Objects.requireNonNull(editClientPermission);
		this.editClientPermission = editClientPermission;
	}
}