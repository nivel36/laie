package ged.web.view.chat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.core.chat.ChatService;
import ged.ejb.core.model.Page;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.view.AbstractView;

@Named
@SessionScoped
public class ChatView extends AbstractView {

	private static final long serialVersionUID = 1L;

	private static final String DEFAULT_EMPTY_MESSAGE = null;
	
	private int activePanel = 0;
	
	private String currentMessage;

	private Map<User,String> messages;

	private String searchText;

	private User selectedUser;

	private List<User> users;

	@Inject
	private transient UserService userService;

	@Inject
	private transient ChatService chatService;
	
	@Inject
	private transient ChatPush chatPush;
	
	
	public int getActivePanel() {
		return this.activePanel;
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
		this.messages = new HashMap<User, String>();
	}

	public void search() {
		this.users = this.userService.search(this.searchText, Page.of(0, 10)).getResultData();
	}

	public void sendMessage() {
		Objects.requireNonNull(getSelectedUser());
		Objects.requireNonNull(getMessages());
		if (getMessages().containsKey(getSelectedUser())) {
			final String message = getCurrentMessage();
			if (message != null && !message.isBlank()) {
				getChatService().sendMessage(getSelectedUser(), message);
				getChatPush().sendMessage(sessionUser.getUser(), getSelectedUser(), message);
			}
			clearSelectedUserMessage();
		}
	}
	
	private void clearSelectedUserMessage() {
		setCurrentMessage(DEFAULT_EMPTY_MESSAGE);
		getMessages().put(getSelectedUser(), DEFAULT_EMPTY_MESSAGE);
	}
	
	public void updateMessage() {
		Objects.requireNonNull(getSelectedUser());
		getMessages().put(getSelectedUser(), getCurrentMessage());
	}
	
	public void selectUser(final User selectedUser) {
		this.selectedUser = selectedUser;
		this.activePanel = 1;
		if (getMessages().containsKey(selectedUser)) {
			setCurrentMessage(getMessages().get(getSelectedUser()));
		} else {
			setCurrentMessage(DEFAULT_EMPTY_MESSAGE);
		}
	}

	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}

	public ChatService getChatService() {
		return chatService;
	}

	public void setChatService(ChatService chatService) {
		this.chatService = chatService;
	}

	private Map<User, String> getMessages() {
		return messages;
	}

	public void setCurrentMessage(String currentMessage) {
		this.currentMessage = currentMessage;
	}

	public String getCurrentMessage() {
		return currentMessage;
	}

	public ChatPush getChatPush() {
		return chatPush;
	}

	public void setChatPush(ChatPush chatPush) {
		this.chatPush = chatPush;
	}
}
