package es.nivel36.laie.ejb.core.chat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.ejb.Stateless;

import es.nivel36.laie.ejb.user.User;

@Stateless
public class ChatService implements Serializable {

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
			return this.date;
		}

		public String getMessage() {
			return this.message;
		}

		@Override
		public String toString() {
			return "Value [date=" + this.date + ", message=" + this.message + "]";
		}
	}

	private static final long serialVersionUID = 1L;

	private final transient Map<User, List<Value>> messages = new HashMap<>();

	public Map<User, List<Value>> getMessages() {
		return this.messages;
	}

	public List<Value> getMessages(final User user) {
		Objects.requireNonNull(user);
		if (this.getMessages().containsKey(user)) {
			return new ArrayList<>();
		}
		return this.getMessages().get(user);
	}

	public void sendMessage(final User user, final String message) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(message);
		if (!this.getMessages().containsKey(user)) {
			this.getMessages().put(user, new ArrayList<>());
		}
		this.getMessages().get(user).add(new Value(LocalDate.now(), message));
		System.out.println(this.getMessages().get(user).toString());
	}
}
