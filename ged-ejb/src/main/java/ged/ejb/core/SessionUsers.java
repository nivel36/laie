package ged.ejb.core;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
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
		Objects.requireNonNull(username);
		final SessionValues sessionValues = this.onlineUsers.get(username);
		sessionValues.lastAction = LocalDateTime.now();
	}

	public LocalDateTime getLastAction(final String username) {
		Objects.requireNonNull(username);
		final SessionValues sessionValues = this.onlineUsers.get(username);
		return sessionValues.lastAction;
	}

	public Set<String> getOnlineUsers() {
		return this.onlineUsers.keySet();
	}

	public Set<String> getSessionId(final String username) {
		return this.onlineUsers.get(username).sessionIds;
	}

	public boolean isOnline(final String username) {
		return this.onlineUsers.containsKey(username);
	}

	public void add(final String username, final String sessionId) {
		final SessionValues values = new SessionValues(sessionId);
		this.onlineUsers.put(username, values);
	}

	public void remove(final String username, final String sessionId) {
		final SessionValues sessionValues = this.onlineUsers.get(username);
		sessionValues.sessionIds.remove(sessionId);
		if (sessionValues.sessionIds.isEmpty()) {
			this.onlineUsers.remove(username);
		}
	}
}