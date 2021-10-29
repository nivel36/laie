package es.nivel36.laie.web.view.curriculum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;

import es.nivel36.laie.ejb.curriculum.Curriculum;
import es.nivel36.laie.ejb.curriculum.CurriculumService;
import es.nivel36.laie.ejb.curriculum.language.Language;
import es.nivel36.laie.ejb.curriculum.language.LanguageLevel;
import es.nivel36.laie.web.core.IllegalPageStateException;
import es.nivel36.laie.web.core.util.PageEnum;
import es.nivel36.laie.web.core.view.AbstractView;

@Named
@ViewScoped
public class LanguageView extends AbstractView {

	private static final long serialVersionUID = 1L;

	@Inject
	@Param(name = "curriculumId", required = true)
	private Curriculum curriculum;

	@Inject
	private transient CurriculumService curriculumService;

	private String languageLevel;

	private List<LanguageLevel> languageLevels;

	private String languageName;

	private List<Language> languages;

	private String curriculumUrl() {
		final Map<String, String> queryParams = new HashMap<>();
		queryParams.put("id", curriculum.getUid());
		queryParams.put("candidateId", this.curriculum.getCandidate().getUid());
		return this.navigator.getRedirectUrl(PageEnum.CURRICULUM, queryParams);
	}

	public void deleteLanguage(final Language language) {
		this.languages.remove(language);
	}

	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	public String getLanguageLevel() {
		return this.languageLevel;
	}

	public List<LanguageLevel> getLanguageLevels() {
		return this.languageLevels;
	}

	public String getLanguageName() {
		return this.languageName;
	}

	public List<Language> getLanguages() {
		return this.languages;
	}

	@PostConstruct
	public void init() {
		if (this.curriculum == null) {
			throw new IllegalPageStateException("Null curriculum");
		}
		this.languageLevels = Arrays.asList(LanguageLevel.values());
		this.languages = new ArrayList<>(this.curriculum.getLanguages());
	}

	public void newLanguage() {
		final Language newLanguage = new Language();
		newLanguage.setName(this.languageName);
		newLanguage.setLevel(LanguageLevel.valueOf(this.languageLevel));
		newLanguage.setCurriculum(this.curriculum);
		this.languages.add(newLanguage);
		this.languageName = null;
		this.languageLevel = null;
	}

	public String save() {
		this.curriculum.setLanguages(new HashSet<Language>(this.languages));
		this.curriculumService.save(this.curriculum);
		return this.curriculumUrl();
	}

	public void setCurriculumService(final CurriculumService curriculumService) {
		this.curriculumService = curriculumService;
	}

	public void setLanguageLevel(final String languageLevel) {
		this.languageLevel = languageLevel;
	}

	public void setLanguageName(final String languageName) {
		this.languageName = languageName;
	}

	public void setLanguages(final List<Language> languages) {
		this.languages = languages;
	}
}
