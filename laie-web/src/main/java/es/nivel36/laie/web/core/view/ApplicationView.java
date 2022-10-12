package es.nivel36.laie.web.core.view;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import es.nivel36.laie.ejb.core.Language;

@ApplicationScoped
@Named
public class ApplicationView extends AbstractView {

	private static final long serialVersionUID = 1L;

	private List<Language> languages;

	public List<Language> getLanguages() {
		return languages;
	}

	@PostConstruct
	public void init() {
		this.languages = Arrays.asList(Language.values());
	}
}
