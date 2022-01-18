package es.nivel36.laie.web.view.chat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.inject.Named;

import es.nivel36.laie.ejb.user.User;

@Named
@ApplicationScoped
public class ChatPush implements Serializable {

	/*
	 * Si se cambia el nombre de las variables se debe modificar también en
	 * right-panel.xhtml
	 */
	public static class MessageInfo implements Serializable {

		private static final long serialVersionUID = 1L;

		private final String date;

		private final boolean firstMsgOfDay;

		private final String message;

		private final String time;

		private final String username;

		public MessageInfo(final String username, final String message, final String date, final boolean firstMsgOfDay,
				final String time) {
			super();
			Objects.requireNonNull(username);
			Objects.requireNonNull(message);
			Objects.requireNonNull(date);
			this.username = username;
			this.message = message;
			this.date = date;
			this.firstMsgOfDay = firstMsgOfDay;
			this.time = time;
		}

		public String getDate() {
			return this.date;
		}

		public String getMessage() {
			return this.message;
		}

		public String getTime() {
			return this.time;
		}

		public String getUsername() {
			return this.username;
		}

		public boolean isFirstMsgOfDay() {
			return this.firstMsgOfDay;
		}
	}

	private static final long serialVersionUID = 1L;

	private int i = 0;

	@Inject
	@Push(channel = "chat")
	private PushContext push;

	public PushContext getPush() {
		return this.push;
	}

	public void sendMessage(final User fromUser, final User toUser, final String message) {
		Objects.requireNonNull(fromUser);
		Objects.requireNonNull(toUser);
		Objects.requireNonNull(message);
		final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' uuuu"); // TODO
		final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm:ss");
		final MessageInfo info = new MessageInfo(fromUser.getName(), message, dateFormat.format(LocalDate.now()),
				(this.i++ % 3) == 0, timeFormat.format(LocalTime.now()));
		this.getPush().send(info, fromUser);
		this.getPush().send(info, toUser);
	}

	public void setPush(final PushContext push) {
		this.push = push;
	}
}
