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

	private List<LanguageLevel> languageLevels;

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

	public List<LanguageLevel> getLanguageLevels() {
		return this.languageLevels;
	}

	public List<Language> getLanguages() {
		return this.languages;
	}

	@PostConstruct
	public void init() {
		this.curriculum = this.getValueFromFlash(CURRICULUM_KEY);
		this.languageLevels = this.curriculumService.findAllLanguageLevels();
		this.languages = new ArrayList<>(this.curriculum.getLanguages());
	}

	public void newLanguage() {
		this.languages.add(new Language());
	}

	public String save() {
		this.curriculum.setLanguages(new HashSet<Language>(this.languages));
		this.curriculumService.save(this.curriculum);
		return this.curriculumUrl();
	}

	public void setCurriculumService(final CurriculumService curriculumService) {
		this.curriculumService = curriculumService;
	}

	public void setLanguages(final List<Language> languages) {
		this.languages = languages;
	}
}
