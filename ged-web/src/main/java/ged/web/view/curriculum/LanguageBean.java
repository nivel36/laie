package ged.web.view.curriculum;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.curriculum.Curriculum;
import ged.ejb.curriculum.CurriculumService;
import ged.ejb.curriculum.language.Language;
import ged.ejb.curriculum.language.LanguageService;
import ged.web.core.view.AbstractDialogBean;

@Named
@ViewScoped
public class LanguageBean extends AbstractDialogBean {

	private static final long serialVersionUID = -3985331305200647310L;

	@Inject
	private transient CurriculumService curriculumService;

	private Language language;

	@Inject
	private transient LanguageService languageService;

	public void delete() {
		if (!this.isNewLanguage()) {
			this.languageService.delete(this.language);
			this.closeDialog();
		}
	}

	public Language getLanguage() {
		return this.language;
	}

	@PostConstruct
	public void init() {
		final Long languageId = this.getIdFromParameters("languageId");
		if (languageId != null) {
			this.language = this.languageService.find(languageId);
		}
		if (this.language == null) {
			final Long curriculumId = this.getIdFromParameters("curriculumId");
			final Curriculum curriculum = this.curriculumService.find(curriculumId);
			this.language = new Language();
			this.language.setCurriculum(curriculum);
		}
	}

	public boolean isNewLanguage() {
		return this.language.getId() == 0;
	}

	public void save() {
		if (this.isNewLanguage()) {
			this.languageService.insert(this.language);
		}
		else {
			this.language = this.languageService.update(this.language);
		}
		this.closeDialog(this.language);
	}

	public void setLanguage(final Language language) {
		this.language = language;
	}
}
