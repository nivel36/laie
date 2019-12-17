package ged.ejb.core.mail;

public class Mail {

	private final String to;

	private final String from;

	private final String subject;

	private final String message;

	public Mail(String to, String from, String subject, String message) {
		this.to = to;
		this.from = from;
		this.subject = subject;
		this.message = message;
	}

	public String getTo() {
		return to;
	}

	public String getFrom() {
		return from;
	}

	public String getSubject() {
		return subject;
	}

	public String getMessage() {
		return message;
	}

}
