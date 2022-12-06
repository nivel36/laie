package es.nivel36.laie.web.view.user;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.web.core.view.AbstractView;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class SelectUserView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(SelectUserView.class);

	private static final long serialVersionUID = 1L;

	private List<Long> alredySelected;

	private String searchText;

	private User selectedUser;

	@Inject
	private UserLazyDataModel users;

	public void export() {
		logger.debug("Export users action performed");
	}

	public List<Long> getAlredySelected() {
		return this.alredySelected;
	}

	public String getSearchText() {
		return this.searchText;
	}

	public User getSelectedUser() {
		return this.selectedUser;
	}

	public UserLazyDataModel getUsers() {
		return this.users;
	}

	@PostConstruct
	public void init() {
		logger.trace("User search init");
		this.alredySelected = this.initAlredySelectedCandidates();
	}

	private List<Long> initAlredySelectedCandidates() {
		final List<Long> userIds = new ArrayList<>();
		final String userIdParameter = this.externalContext.getRequestParameterMap().get("usersId");
		if (userIdParameter != null) {
			final String[] ids = userIdParameter.split("\\|");
			for (final String id : ids) {
				userIds.add(Long.valueOf(id));
			}
		}
		return userIds;
	}

	public void onUserSelect() {
		PrimeFaces.current().dialog().closeDynamic(this.selectedUser);
	}

	public void search() {
		logger.debug("Search users action performed");
		this.users.setSearchText(this.searchText);
	}

	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}

	public void setSelectedUser(final User selectedUser) {
		this.selectedUser = selectedUser;
	}

	public void setUsers(final UserLazyDataModel users) {
		this.users = users;
	}
}