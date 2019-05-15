package ged.web.view.curriculum;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.curriculum.Curriculum;
import ged.ejb.curriculum.language.Language;
import ged.ejb.curriculum.language.LanguageService;
import ged.web.core.util.PageEnum;
import ged.web.core.view.AbstractDialogBean;

@Named
@ViewScoped
public class LanguageBean extends AbstractDialogBean {

	private static final long serialVersionUID = -3985331305200647310L;

	private static final String CURRICULUM_KEY = "curriculum";

	private static final String LANGUAGE_KEY = "language";

	private Language language;

	@Inject
	private transient LanguageService languageService;

	public String delete() {
		this.languageService.delete(this.language);
		return curriculumUrl();
	}

	public Language getLanguage() {
		return this.language;
	}

	private Language initLanguage() {
		Language language = this.getValueFromFlash(LANGUAGE_KEY);
		if (language == null) {
			final Curriculum curriculum = this.getValueFromFlash(CURRICULUM_KEY);
			language = new Language();
			language.setCurriculum(curriculum);
		}
		return language;
	}

	@PostConstruct
	public void init() {
		this.language = initLanguage();
	}

	public boolean isNewLanguage() {
		return this.language.getId() == 0;
	}

	public String save() {
		this.language = this.languageService.save(this.language);
		return curriculumUrl();
	}

	public void setLanguage(final Language language) {
		this.language = language;
	}

	private String curriculumUrl() {
		return PageEnum.CURRICULUM.getRedirectUrl(this.language.getCurriculum().getCandidate());
	}
}
