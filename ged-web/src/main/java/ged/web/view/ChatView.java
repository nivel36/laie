package ged.web.view;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.core.model.Page;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.view.AbstractView;

@Named
@SessionScoped
public class ChatView extends AbstractView {

	private static final long serialVersionUID = 1L;

	private int activePanel = 0;

	private String message;

	private String searchText;

	private User selectedUser;

	private List<User> users;

	@Inject
	private transient UserService userService;

	public int getActivePanel() {
		return this.activePanel;
	}

	public String getMessage() {
		return this.message;
	}

	public String getSearchText() {
		return this.searchText;
	}

	public User getSelectedUser() {
		return this.selectedUser;
	}

	public List<User> getUsers() {
		return this.users;
	}

	@PostConstruct
	public void init() {
		this.users = this.userService.findAll(Page.of(0, 10));
		this.users.remove(this.sessionUser.get());
		this.selectedUser = this.users.get(0);
	}

	public void search() {
		this.users = this.userService.search(this.searchText, Page.of(0, 10)).getResultData();
	}

	public void selectUser(final User selectedUser) {
		this.selectedUser = selectedUser;
		this.activePanel = 1;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}
}
