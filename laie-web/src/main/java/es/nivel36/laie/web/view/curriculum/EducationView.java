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

import es.nivel36.laie.ejb.curriculum.CurriculumDto;
import es.nivel36.laie.ejb.curriculum.CurriculumService;
import es.nivel36.laie.ejb.curriculum.education.EducationDto;
import es.nivel36.laie.web.core.IllegalPageStateException;
import es.nivel36.laie.web.core.util.PageEnum;
import es.nivel36.laie.web.core.view.AbstractView;

@Named
@ViewScoped
public class EducationView extends AbstractView {

	private static final long serialVersionUID = 7140292965204508410L;

	private CurriculumDto curriculum;

	private EducationDto education;

	private List<Integer> years;
	
	private String candidateUid;

	@Inject
	private transient CurriculumService curriculumService;

	@PostConstruct
	public void init() {
		final String educationUid = this.getValueFromGetParameters("education");
		this.candidateUid = this.getValueFromGetParameters("candidate");
		final String curriculumUid = this.getValueFromGetParameters("curriculum");
		this.curriculum = this.curriculumService.findCurriculumByUid(curriculumUid);
		this.education = this.initEducation(educationUid);
		this.years = this.initYears();
	}

	private EducationDto initEducation(final String educationUid) {
		if (educationUid == null) {
			return new EducationDto();
		}
		try {
			final int item = Integer.parseInt(educationUid);
			final List<EducationDto> educations = new ArrayList<>(this.curriculum.getEducation());
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
		return this.navigator.getRedirectUrl(PageEnum.CURRICULUM, this.curriculum.getUid());
	}

	public String delete() {
		this.curriculum.getEducation().remove(education);
		return this.save();
	}

	public String save() {
		this.curriculumService.addCurriculum(this.candidateUid, this.curriculum);
		return this.buildCurriculumUrl();
	}

	public EducationDto getEducation() {
		return this.education;
	}

	public List<Integer> getYears() {
		return this.years;
	}

	public void setEducation(final EducationDto education) {
		this.education = education;
	}

	public void setCurriculumService(final CurriculumService curriculumService) {
		Objects.requireNonNull(curriculumService);
		this.curriculumService = curriculumService;
	}
}