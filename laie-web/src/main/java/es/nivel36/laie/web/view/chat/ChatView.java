package es.nivel36.laie.web.view.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import es.nivel36.laie.ejb.core.chat.ChatService;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserService;
import es.nivel36.laie.web.core.view.AbstractView;

@Named
@SessionScoped
public class ChatView extends AbstractView {

	private static final String DEFAULT_EMPTY_MESSAGE = null;

	private static final long serialVersionUID = 1L;

	private int activePanel = 0;

	@Inject
	private transient ChatPush chatPush;

	@Inject
	private transient ChatService chatService;

	private String currentMessage;

	private Map<User, String> messages;

	private String searchText;

	private User selectedUser;

	private List<User> users;

	@Inject
	private transient UserService userService;

	private void clearSelectedUserMessage() {
		this.setCurrentMessage(DEFAULT_EMPTY_MESSAGE);
		this.getMessages().put(this.getSelectedUser(), DEFAULT_EMPTY_MESSAGE);
	}

	public int getActivePanel() {
		return this.activePanel;
	}

	public ChatPush getChatPush() {
		return this.chatPush;
	}

	public ChatService getChatService() {
		return this.chatService;
	}

	public String getCurrentMessage() {
		return this.currentMessage;
	}

	private Map<User, String> getMessages() {
		return this.messages;
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
		//TODO:
		this.users = new ArrayList<>();
		this.users.remove(this.sessionUser.get());
		this.selectedUser = this.users.get(0);
		this.messages = new HashMap<>();
	}

	public void search() {
		this.users = new ArrayList<User>();
	}

	public void selectUser(final User selectedUser) {
		this.selectedUser = selectedUser;
		this.activePanel = 1;
		if (this.getMessages().containsKey(selectedUser)) {
			this.setCurrentMessage(this.getMessages().get(this.getSelectedUser()));
		} else {
			this.setCurrentMessage(DEFAULT_EMPTY_MESSAGE);
		}
	}

	public void sendMessage() {
		Objects.requireNonNull(this.getSelectedUser());
		Objects.requireNonNull(this.getMessages());
		if (this.getMessages().containsKey(this.getSelectedUser())) {
			final String message = this.getCurrentMessage();
			if ((message != null) && !message.isBlank()) {
				this.getChatService().sendMessage(this.getSelectedUser(), message);
				this.getChatPush().sendMessage(null, null, message);
			}
			this.clearSelectedUserMessage();
		}
	}

	public void setChatPush(final ChatPush chatPush) {
		this.chatPush = chatPush;
	}

	public void setChatService(final ChatService chatService) {
		this.chatService = chatService;
	}

	public void setCurrentMessage(final String currentMessage) {
		this.currentMessage = currentMessage;
	}

	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}

	public void updateMessage() {
		Objects.requireNonNull(this.getSelectedUser());
		this.getMessages().put(this.getSelectedUser(), this.getCurrentMessage());
	}
}
