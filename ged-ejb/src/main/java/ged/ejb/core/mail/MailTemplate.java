package ged.ejb.core.mail;

import java.util.Map;

public class MailTemplate {

	private final String messageTemplate;

	private final String name;

	private final String subject;

	public MailTemplate(final String name, final String messageTemplate, final String subject) {
		this.name = name;
		this.messageTemplate = messageTemplate;
		this.subject = subject;
	}

	public String buildMessage(final Map<String, String> parameters) {
		String message = this.messageTemplate;
		for (final  Map.Entry<String, String> entry : parameters.entrySet()) {
			final String value = entry.getValue();
			message = message.replace("%" + entry.getKey() + "%", value);
		}
		return message;
	}

	public String getMessageTemplate() {
		return this.messageTemplate;
	}

	public String getName() {
		return this.name;
	}

	public String getSubject() {
		return this.subject;
	}
}