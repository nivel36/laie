package ged.web.view.curriculum;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.curriculum.Curriculum;
import ged.ejb.curriculum.CurriculumService;
import ged.ejb.curriculum.Education;
import ged.web.core.util.PageEnum;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class EducationBean extends AbstractBean {

	private static final long serialVersionUID = -7120837113945432637L;

	private static final String EDUCATION_KEY = "education";
	
	private static final String CURRICULUM_KEY = "curriculum";

	private Education education;

	private List<Integer> years;
	
	private Curriculum curriculum;

	@Inject
	private transient CurriculumService curriculumService;

	public String delete() {
		this.curriculumService.save(curriculum);
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
		this.years = initYears();
		this.curriculum = initCurriculum();
	}

	public List<Integer> initYears() {
		List<Integer> years = new ArrayList<Integer>();
		for (int i = 1950; i < LocalDate.now().getYear(); i++) {
			years.add(Integer.valueOf(i));
		}
		return years;
	}
	
	private Curriculum initCurriculum() {
		Curriculum curriculum = getValueFromFlash(CURRICULUM_KEY);
		if(isNewEducation()) {
			curriculum.removeEducation(education);
		}
		return curriculum;
	}

	public List<Integer> getYears() {
		return years;
	}

	public Education initEducation() {
		Education education = this.getValueFromFlash(EDUCATION_KEY);
		if (education == null) {
			education = new Education();
			education.setStillStudying(false);
			education.setCurriculum(this.curriculum);
		}
		return education;
	}

	public boolean isNewEducation() {
		return this.education.getId() == 0;
	}

	public String save() {
		this.curriculum.addEducation(this.education);
		this.curriculumService.save(this.curriculum);
		return curriculumUrl();
	}

	public void setEducation(final Education education) {
		this.education = education;
	}

	public void setCurriculumService(CurriculumService curriculumService) {
		this.curriculumService = curriculumService;
	}
}