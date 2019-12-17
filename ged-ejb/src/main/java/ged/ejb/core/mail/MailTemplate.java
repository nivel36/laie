package ged.ejb.core.mail;

import java.util.Map;

public class MailTemplate {

	private final String messageTemplate;

	private final String name;

	private final String subject;

	public MailTemplate(String name, String messageTemplate, String subject) {
		this.name = name;
		this.messageTemplate = messageTemplate;
		this.subject = subject;
	}

	public String getMessageTemplate() {
		return messageTemplate;
	}

	public String getName() {
		return name;
	}

	public String getSubject() {
		return subject;
	}

	public String buildMessage(final Map<String, String> parameters) {
		String message = messageTemplate;
		for (String key : parameters.keySet()) {
			String value = parameters.get(key);
			message.replace("%" + key + "%", value);
		}
		return message;
	}
}