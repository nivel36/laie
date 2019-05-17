package ged.web.view.curriculum;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.curriculum.Curriculum;
import ged.ejb.curriculum.CurriculumService;
import ged.ejb.curriculum.Language;
import ged.ejb.curriculum.LanguageLevel;
import ged.web.core.IllegalPageStateException;
import ged.web.core.util.PageEnum;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class LanguageBean extends AbstractBean {

	private static final String CURRICULUM_KEY = "curriculum";

	private static final long serialVersionUID = -3985331305200647310L;

	private Curriculum curriculum;

	@Inject
	private transient CurriculumService curriculumService;

	private String languageLevel;

	private List<LanguageLevel> languageLevels;

	private String languageName;

	private List<Language> languages;

	private String curriculumUrl() {
		return PageEnum.CURRICULUM.getRedirectUrl(this.curriculum.getCandidate());
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
		this.curriculum = this.getValueFromFlash(CURRICULUM_KEY);
		if (this.curriculum == null) {
			throw new IllegalPageStateException("Null curriculum");
		}
		this.languageLevels = this.curriculumService.findAllLanguageLevels();
		this.languages = new ArrayList<>(this.curriculum.getLanguages());
	}

	public void newLanguage() {
		final Language newLanguage = new Language();
		newLanguage.setName(this.languageName);
		newLanguage.setLevel(this.languageLevel);
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
