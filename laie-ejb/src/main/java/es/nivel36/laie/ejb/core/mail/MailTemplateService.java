package es.nivel36.laie.ejb.core.mail;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Stateless;

@Stateless
public class MailTemplateService {

	private Map<String, MailTemplate> templates;

	public MailTemplate findMailTemplate(final String templateName) {
		Objects.requireNonNull(templateName);
		return this.templates.get(templateName);
	}

	@PostConstruct
	public void init() {
		this.templates = new HashMap<>();
	}
}
