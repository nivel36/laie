package es.nivel36.laie.web.view.client;

import javax.faces.view.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.web.core.view.AbstractView;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class SearchClientView extends AbstractView {

	private static final long serialVersionUID = -8547192185427619599L;

	private static final Logger logger = LoggerFactory.getLogger(SearchClientView.class);

	private @Inject ClientLazyDataModel clients;

	private transient @Inject AddClientPermission addClientPermission;

	private boolean insertable;

	private String searchText;

	@PostConstruct
	public void init() {
		this.insertable = addClientPermission.validate(null);
	}

	public void search() {
		logger.debug("Search clients action performed");
		if (this.searchText != null && this.searchText.length() > 2) {
			this.clients.setSearchText(this.searchText);
		} else {
			this.searchText = null;
			this.clients.setSearchText(null);
		}
	}

	public boolean isInsertable() {
		return insertable;
	}

	public void setAddClientPermission(AddClientPermission addClientPermission) {
		this.addClientPermission = addClientPermission;
	}

	public void export() {
		logger.debug("Export clients action performed");
	}

	public ClientLazyDataModel getClients() {
		return this.clients;
	}

	public String getSearchText() {
		return this.searchText;
	}

	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}
}
