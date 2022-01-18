package es.nivel36.laie.ejb.core.mail;

public class Mail {

	private final String from;

	private final String message;

	private final String subject;

	private final String to;

	public Mail(final String to, final String from, final String subject, final String message) {
		this.to = to;
		this.from = from;
		this.subject = subject;
		this.message = message;
	}

	public String getFrom() {
		return this.from;
	}

	public String getMessage() {
		return this.message;
	}

	public String getSubject() {
		return this.subject;
	}

	public String getTo() {
		return this.to;
	}

}
