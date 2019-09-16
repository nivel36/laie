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
	
	private User selectedUser;
	
	private List<User> users;

	@Inject
	private transient UserService userService;

	public int getActivePanel() {
		return activePanel;
	}

	public User getSelectedUser() {
		return selectedUser;
	}

	public List<User> getUsers() {
		return users;
	}

	@PostConstruct
	public void init() {
		users = userService.findAll(Page.ALL);
		users.remove(this.sessionUser.get());
		selectedUser = users.get(0);
	}

	public void selectUser(User selectedUser) {
		this.selectedUser = selectedUser;
		this.activePanel = 1;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
