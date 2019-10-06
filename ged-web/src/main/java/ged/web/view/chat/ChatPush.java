package ged.web.view.chat;

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

import ged.ejb.user.User;

@Named
@ApplicationScoped
public class ChatPush implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	@Push(channel = "chat")
	private PushContext push;

	private int i = 0;
	
	public void sendMessage(final User fromUser, final User toUser, final String message) {
		Objects.requireNonNull(fromUser);
		Objects.requireNonNull(toUser);
		Objects.requireNonNull(message);
		final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' uuuu"); // TODO
		final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm:ss");
		final MessageInfo info = new MessageInfo(fromUser.getName(), message, dateFormat.format(LocalDate.now()), i++ % 3 == 0, timeFormat.format(LocalTime.now()));
		getPush().send(info, fromUser);
		getPush().send(info, toUser);
	}
	
	public PushContext getPush() {
		return push;
	}

	public void setPush(PushContext push) {
		this.push = push;
	}
	
	/*
	 * Si se cambia el nombre de las variables se debe modificar también en right-panel.xhtml
	 */
	public static class MessageInfo implements Serializable {
		
		private static final long serialVersionUID = 1L;

		private final String username;
		
		private final String message;
		
		private final String date;
		
		private final boolean firstMsgOfDay;
		
		private final String time;
		
		public MessageInfo(final String username, final String message, final String date, final boolean firstMsgOfDay, final String time) {
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

		public String getUsername() {
			return username;
		}

		public String getMessage() {
			return message;
		}

		public String getDate() {
			return date;
		}

		public boolean isFirstMsgOfDay() {
			return firstMsgOfDay;
		}

		public String getTime() {
			return time;
		}
	}
}
