package es.nivel36.laie.web.view.curriculum;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;

import es.nivel36.laie.ejb.curriculum.Curriculum;
import es.nivel36.laie.ejb.curriculum.CurriculumService;
import es.nivel36.laie.ejb.curriculum.education.Education;
import es.nivel36.laie.web.core.IllegalPageStateException;
import es.nivel36.laie.web.core.util.PageEnum;
import es.nivel36.laie.web.core.view.AbstractView;

@Named
@ViewScoped
public class EducationView extends AbstractView {

	private static final long serialVersionUID = 1L;

	@Inject
	@Param(name = "curriculumId", required = true)
	private Curriculum curriculum;

	@Inject
	private transient CurriculumService curriculumService;

	private Education education;

	private List<Integer> years;

	private String buildCurriculumUrl() {
		final Map<String, String> queryParams = new HashMap<>();
		queryParams.put("id", this.curriculum.getUid());
		queryParams.put("candidateId", this.curriculum.getCandidate().getUid());
		return this.navigator.getRedirectUrl(PageEnum.CURRICULUM, queryParams);
	}

	private Education buildEducationFromQueryParameter(final String itemParameter) {
		try {
			final int item = Integer.parseInt(itemParameter);
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

	private Education buildNewEducation() {
		final Education newEducation = new Education();
		newEducation.setCurriculum(this.curriculum);
		return newEducation;
	}

	public String delete() {
		this.curriculum.removeEducation(this.education);
		this.curriculumService.save(this.curriculum);
		return this.buildCurriculumUrl();
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
	}

	public Education initEducation() {
		final String itemParameter = this.getValueFromGetParameters("item");
		if (itemParameter == null) {
			return this.buildNewEducation();
		} else {
			return this.buildEducationFromQueryParameter(itemParameter);
		}
	}

	public List<Integer> initYears() {
		final List<Integer> newYears = new ArrayList<>();
		for (int i = 1950; i < LocalDate.now().getYear(); i++) {
			newYears.add(Integer.valueOf(i));
		}
		return newYears;
	}

	public boolean isNewEducation() {
		return this.education.getId() == 0;
	}

	public String save() {
		if (education.isNew()) {
			this.curriculum.addEducation(this.education);
		}
		this.curriculumService.save(this.curriculum);
		return this.buildCurriculumUrl();
	}

	public void setCurriculumService(final CurriculumService curriculumService) {
		this.curriculumService = curriculumService;
	}

	public void setEducation(final Education education) {
		this.education = education;
	}
}