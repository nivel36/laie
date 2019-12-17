package ged.ejb.core.mail;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

@Stateless
public class MailTemplateService {

	private Map<String, MailTemplate> templates;

	@PostConstruct
	public void init() {
		templates = new HashMap<>();
	}

	public MailTemplate findMailTemplate(String templateName) {
		Objects.requireNonNull(templateName);
		return templates.get(templateName);
	}
}
