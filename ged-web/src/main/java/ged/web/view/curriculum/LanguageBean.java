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

	public Curriculum getCurriculum() {
		return curriculum;
	}

	public List<LanguageLevel> getLanguageLevels() {
		return languageLevels;
	}

	public List<Language> getLanguages() {
		return languages;
	}

	@PostConstruct
	public void init() {
		this.curriculum = this.getValueFromFlash(CURRICULUM_KEY);
		this.languageLevels = curriculumService.findAllLanguageLevels();
		this.languages = new ArrayList<>(this.curriculum.getLanguages());
	}

	public String save() {
		this.curriculum.setLanguages(new HashSet<Language>(languages));
		this.curriculumService.save(this.curriculum);	
		return curriculumUrl();
	}

	public void setCurriculumService(CurriculumService curriculumService) {
		this.curriculumService = curriculumService;
	}

	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}
}
