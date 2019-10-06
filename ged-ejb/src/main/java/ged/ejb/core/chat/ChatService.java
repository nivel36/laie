package ged.ejb.core.chat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.ejb.Stateless;

import ged.ejb.user.User;

@Stateless
public class ChatService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final Map<User, List<Value>> messages = new HashMap<User, List<Value>>();

	public void sendMessage(final User user, final String message) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(message);
		if (!getMessages().containsKey(user)) {
			getMessages().put(user, new ArrayList<>());
		}
		getMessages().get(user).add(new Value(LocalDate.now(), message));
		System.out.println(getMessages().get(user).toString());
	}
	
	public List<Value> getMessages(final User user) {
		Objects.requireNonNull(user);
		if (getMessages().containsKey(user)) {
			return new ArrayList<>();
		}
		return getMessages().get(user);
	}
	
	private static class Value {
		
		private final LocalDate date;
		
		private final String message;
		
		public Value(final LocalDate date, final String message) {
			super();
			Objects.requireNonNull(date);
			Objects.requireNonNull(message);
			this.date = date;
			this.message = message;
		}

		public LocalDate getDate() {
			return date;
		}

		public String getMessage() {
			return message;
		}

		@Override
		public String toString() {
			return "Value [date=" + date + ", message=" + message + "]";
		}
	}

	public Map<User, List<Value>> getMessages() {
		return messages;
	}
}
