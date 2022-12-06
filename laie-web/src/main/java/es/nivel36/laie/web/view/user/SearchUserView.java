package es.nivel36.laie.web.view.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.web.core.view.AbstractView;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class SearchUserView extends AbstractView {

	private static final long serialVersionUID = -8054971408222722286L;

	private static final Logger logger = LoggerFactory.getLogger(SearchUserView.class);

	private String searchText;

	private @Inject UserLazyDataModel users;

	public void search() {
		logger.debug("Search users action performed");
		if (this.searchText != null && this.searchText.length() > 2) {
			this.users.setSearchText(this.searchText);
		} else {
			this.searchText = null;
			this.users.setSearchText(null);
		}
	}

	public String getSearchText() {
		return this.searchText;
	}

	public UserLazyDataModel getUsers() {
		return this.users;
	}

	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}

	public void setUsers(final UserLazyDataModel users) {
		this.users = users;
	}

}