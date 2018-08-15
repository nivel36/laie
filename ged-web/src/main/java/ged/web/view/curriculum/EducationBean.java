package ged.web.view.curriculum;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
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
		if (this.isNewEducation()) {
			this.educationService.insert(this.education);
		}
		else {
			this.education = this.educationService.update(this.education);
		}
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

	public boolean validateDates(final FacesContext context, final List<UIInput> components, final List<Object> values) {
		boolean inputStillWorking = false;
		Integer inputStartYear = null;
		Integer inputEndYear = null;

		final int size = components.size();
		for (int i = 0; i < size; i++) {
			final UIInput component = components.get(i);
			if (component.getId().equals("stillWorking")) {
				if (values.get(i) == null) {
					inputStillWorking = false;
				}
				else {
					inputStillWorking = (Boolean) values.get(i);
				}
			}
			else if (component.getId().equals("startYear")) {
				inputStartYear = (Integer) values.get(i);
			}
			else if (component.getId().equals("endYear")) {
				inputEndYear = (Integer) values.get(i);
			}
		}
		if (inputStillWorking) {
			return true;
		}
		if ((inputStartYear - inputEndYear) > 0) {
			return false;
		}
		return true;
	}
}