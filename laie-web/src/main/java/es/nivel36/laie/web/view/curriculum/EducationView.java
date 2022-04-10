package es.nivel36.laie.web.view.curriculum;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import es.nivel36.laie.ejb.curriculum.Curriculum;
import es.nivel36.laie.ejb.curriculum.CurriculumService;
import es.nivel36.laie.ejb.curriculum.education.Education;
import es.nivel36.laie.web.core.IllegalPageStateException;
import es.nivel36.laie.web.core.view.AbstractView;

@Named
@ViewScoped
public class EducationView extends AbstractView {

	private static final long serialVersionUID = 7140292965204508410L;

	private Curriculum curriculum;

	private Education education;

	private List<Integer> years;
	
	private String candidateId;

	@Inject
	private transient CurriculumService curriculumService;

	@PostConstruct
	public void init() {
		final String educationId = this.getValueFromGetParameters("education");
		this.candidateId = this.getValueFromGetParameters("candidate");
		final String curriculumId = this.getValueFromGetParameters("curriculum");
		this.curriculum = this.curriculumService.findCurriculumById(curriculumId);
		this.education = this.initEducation(educationId);
		this.years = this.initYears();
	}

	private Education initEducation(final String educationId) {
		if (educationId == null) {
			return new Education();
		}
		try {
			final int item = Integer.parseInt(educationId);
			final List<Education> educations = new ArrayList<>(this.curriculum.getEducation());
			if (item >= educations.size()) {
				throw new IllegalPageStateException();
			}
			Collections.sort(educations);
			return educations.get(item);
		} catch (final NumberFormatException e) {
			throw new IllegalPageStateException();
		}
	}
	

	public List<Integer> initYears() {
		final List<Integer> newYears = new ArrayList<>();
		for (int i = 1950; i < LocalDate.now().getYear(); i++) {
			newYears.add(Integer.valueOf(i));
		}
		return newYears;
	}

	private String buildCurriculumUrl() {
		return null;
	}

	public String delete() {
		this.curriculum.getEducation().remove(education);
		return this.save();
	}

	public String save() {
		this.curriculumService.addCurriculum(this.candidateId, this.curriculum);
		return this.buildCurriculumUrl();
	}

	public Education getEducation() {
		return this.education;
	}

	public List<Integer> getYears() {
		return this.years;
	}

	public void setEducation(final Education education) {
		this.education = education;
	}

	public void setCurriculumService(final CurriculumService curriculumService) {
		Objects.requireNonNull(curriculumService);
		this.curriculumService = curriculumService;
	}
}