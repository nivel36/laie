package ged.ejb.core;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SessionUsers {

	private class SessionValues {

		private LocalDateTime lastAction;

		private final Set<String> sessionIds;

		SessionValues(final String sessionId) {
			this.sessionIds = new HashSet<>();
			this.sessionIds.add(sessionId);
			this.lastAction = LocalDateTime.now();
		}
	}

	private final Map<String, SessionValues> onlineUsers = new HashMap<>();

	public void action(final String username) {
		this.onlineUsers.get(username).lastAction = LocalDateTime.now();
	}

	public Set<String> getSessionId(final String username) {
		return this.onlineUsers.get(username).sessionIds;
	}

	public boolean isOnline(final String username) {
		return this.onlineUsers.containsKey(username);
	}

	public LocalDateTime getLastAction(final String username) {
		return this.onlineUsers.get(username).lastAction;
	}

	public void login(final String username, final String sessionId) {
		final SessionValues values = new SessionValues(sessionId);
		this.onlineUsers.put(username, values);
	}

	public void logout(final String username) {
		this.onlineUsers.remove(username);
	}
}