package ged.web.view.curriculum;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.curriculum.Curriculum;
import ged.ejb.curriculum.CurriculumService;
import ged.ejb.curriculum.education.Education;
import ged.ejb.curriculum.education.EducationService;
import ged.web.core.view.AbstractDialogBean;

@Named
@ViewScoped
public class EducationBean extends AbstractDialogBean {

	private static final long serialVersionUID = -7120837113945432637L;

	@Inject
	private transient CurriculumService curriculumService;

	private Education education;

	@Inject
	private transient EducationService educationService;

	public void delete() {
		if (!this.isNewEducation()) {
			this.educationService.delete(this.education);
		}
		this.closeDialog();
	}

	public Education getEducation() {
		return this.education;
	}

	@PostConstruct
	public void init() {
		final Long eduationId = this.getIdFromParameters("educationId");
		if (eduationId != null) {
			this.education = this.educationService.find(eduationId);
		}
		if (this.education == null) {
			final Long curriculumId = this.getIdFromParameters("curriculumId");
			final Curriculum curriculum = this.curriculumService.find(curriculumId);
			this.education = new Education();
			this.education.setStillStudying(true);
			this.education.setCurriculum(curriculum);
		}
	}

	public boolean isNewEducation() {
		return this.education.getId() == 0;
	}

	public void save() {
		this.education = this.educationService.save(this.education);
		this.closeDialog(this.education);
	}

	public void setCurriculumService(final CurriculumService curriculumService) {
		this.curriculumService = curriculumService;
	}

	public void setEducation(final Education education) {
		this.education = education;
	}

	public void setEducationService(final EducationService educationService) {
		this.educationService = educationService;
	}
}