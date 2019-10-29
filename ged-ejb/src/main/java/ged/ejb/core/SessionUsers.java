package ged.ejb.core;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SessionUsers {

	private Set<String> onlineUsers = new HashSet<>();

	public boolean isOnline(String username) {
		return onlineUsers.contains(username);
	}

	public void login(String username) {
		onlineUsers.add(username);
	}

	public void logout(String username) {
		onlineUsers.remove(username);
	}
}
