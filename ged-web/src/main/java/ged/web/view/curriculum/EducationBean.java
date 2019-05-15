package ged.web.view.curriculum;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.curriculum.Curriculum;
import ged.ejb.curriculum.education.Education;
import ged.ejb.curriculum.education.EducationService;
import ged.web.core.util.PageEnum;
import ged.web.core.view.AbstractDialogBean;

@Named
@ViewScoped
public class EducationBean extends AbstractDialogBean {

	private static final long serialVersionUID = -7120837113945432637L;

	private static final String CURRICULUM_KEY = "curriculum";

	private static final String EDUCATION_KEY = "education";

	private Education education;

	@Inject
	private transient EducationService educationService;

	public String delete() {
		this.educationService.delete(this.education);
		return curriculumUrl();
	}

	public Education getEducation() {
		return this.education;
	}

	private String curriculumUrl() {
		return PageEnum.CURRICULUM.getRedirectUrl(this.education.getCurriculum().getCandidate());
	}

	@PostConstruct
	public void init() {
		this.education = initEducation();
	}

	public Education initEducation() {
		Education education = this.getValueFromFlash(EDUCATION_KEY);
		if (education == null) {
			final Curriculum curriculum = this.getValueFromFlash(CURRICULUM_KEY);
			education = new Education();
			education.setStillStudying(false);
			education.setCurriculum(curriculum);
		}
		return education;
	}

	public boolean isNewEducation() {
		return this.education.getId() == 0;
	}

	public String save() {
		this.education = this.educationService.save(this.education);
		return curriculumUrl();
	}

	public void setEducation(final Education education) {
		this.education = education;
	}

	public void setEducationService(final EducationService educationService) {
		this.educationService = educationService;
	}
}