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
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class EducationView extends AbstractView {

	private static final String CURRICULUM_KEY = "curriculum";

	private static final String EDUCATION_KEY = "education";

	private static final long serialVersionUID = -7120837113945432637L;

	private Curriculum curriculum;

	@Inject
	private transient CurriculumService curriculumService;

	private Education education;

	private List<Integer> years;

	private String curriculumUrl() {
		return PageEnum.CURRICULUM.getUrl(this.education.getCurriculum().getCandidate());
	}

	public String delete() {
		this.curriculumService.save(this.curriculum);
		return this.curriculumUrl();
	}

	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	public Education getEducation() {
		return this.education;
	}

	public List<Integer> getYears() {
		return this.years;
	}

	@PostConstruct
	public void init() {
		this.education = this.initEducation();
		this.years = this.initYears();
		this.curriculum = this.initCurriculum();
	}

	private Curriculum initCurriculum() {
		final Curriculum curriculum = this.getValueFromFlash(CURRICULUM_KEY);
		if (this.isNewEducation()) {
			curriculum.removeEducation(this.education);
		}
		return curriculum;
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

	public List<Integer> initYears() {
		final List<Integer> years = new ArrayList<Integer>();
		for (int i = 1950; i < LocalDate.now().getYear(); i++) {
			years.add(Integer.valueOf(i));
		}
		return years;
	}

	public boolean isNewEducation() {
		return this.education.getId() == 0;
	}

	public String save() {
		this.curriculum.addEducation(this.education);
		this.curriculumService.save(this.curriculum);
		return this.curriculumUrl();
	}

	public void setCurriculumService(final CurriculumService curriculumService) {
		this.curriculumService = curriculumService;
	}

	public void setEducation(final Education education) {
		this.education = education;
	}
}