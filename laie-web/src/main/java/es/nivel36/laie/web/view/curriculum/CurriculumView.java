package es.nivel36.laie.web.view.curriculum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.curriculum.Curriculum;
import es.nivel36.laie.ejb.curriculum.education.Education;
import es.nivel36.laie.ejb.curriculum.jobexperience.JobExperience;
import es.nivel36.laie.ejb.curriculum.language.Language;
import es.nivel36.laie.ejb.curriculum.skill.Skill;
import es.nivel36.laie.web.core.IllegalPageStateException;
import es.nivel36.laie.web.core.view.AbstractView;

@Named
@ViewScoped
public class CurriculumView extends AbstractView {

	private static final String CANDIDATE_ID = "candidateId";

	private static final String CURRICULUM_ID = "curriculumId";
	
	private static final String ID = "id";

	private static final String ITEM = "item";

	private static final long serialVersionUID = 1L;

	@Inject
	@Param(name = CANDIDATE_ID, required = false)
	private Candidate candidate;

	@Inject
	@Param(name = ID, required = false)
	private Curriculum curriculum;

	private List<Education> education;

	private List<JobExperience> jobExperiences;

	private List<Language> languages;

	private List<String> skills;

	public String editEducation(final Education e) {
		final Map<String, String> queryParams = new HashMap<>();
		queryParams.put(ITEM, String.valueOf(education.indexOf(e)));
		queryParams.put(CURRICULUM_ID, this.curriculum.getUid());
		return null;
	}

	public String editJobExperience(final JobExperience j) {
		final Map<String, String> queryParams = new HashMap<>();
		queryParams.put(ITEM, String.valueOf(jobExperiences.indexOf(j)));
		queryParams.put(CURRICULUM_ID, this.curriculum.getUid());
		return null;
	}

	public String editLanguages() {
		final Map<String, String> queryParams = new HashMap<>();
		queryParams.put(CURRICULUM_ID, this.curriculum.getUid());
		return null;
	}

	public String editSkills() {
		final Map<String, String> queryParams = new HashMap<>();
		queryParams.put(CURRICULUM_ID, this.curriculum.getUid());
		return null;
	}

	public Candidate getCandidate() {
		return this.candidate;
	}

	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	public List<Education> getEducation() {
		return this.education;
	}

	public List<JobExperience> getJobExperiences() {
		return this.jobExperiences;
	}

	public List<Language> getLanguages() {
		return this.languages;
	}

	public List<String> getSkills() {
		return this.skills;
	}

	@PostConstruct
	public void init() {
		if ((this.curriculum == null) && (this.candidate == null)) {
			throw new IllegalPageStateException();
		}
		if (this.curriculum == null) {
			this.curriculum = new Curriculum();
			this.curriculum.setCandidate(this.candidate);
		}
		if (this.curriculum.getSkills() == null) {
			this.curriculum.setSkills(new HashSet<Skill>());
		}
		if (this.curriculum.getEducation() == null) {
			this.curriculum.setEducation(new HashSet<Education>());
		}
		if (this.curriculum.getJobExperiences() == null) {
			this.curriculum.setJobExperiences(new HashSet<JobExperience>());
		}
		if (this.curriculum.getLanguages() == null) {
			this.curriculum.setLanguages(new HashSet<Language>());
		}
		this.skills = new ArrayList<>();
		for (final Skill skill : this.curriculum.getSkills()) {
			this.skills.add(skill.getName());
		}
		this.education = new ArrayList<>(this.curriculum.getEducation());
		this.jobExperiences = new ArrayList<>(this.curriculum.getJobExperiences());
		this.languages = new ArrayList<>(this.curriculum.getLanguages());
		this.orderJobExperiencesByDate();
		this.orderEducation();
		this.orderLanguages();
		this.orderSkills();
	}

	public String newEducation() {
		final Map<String, String> queryParams = new HashMap<>();
		queryParams.put(CURRICULUM_ID, this.curriculum.getUid());
		return null;
	}

	public String newJobExperience() {
		final Map<String, String> queryParams = new HashMap<>();
		queryParams.put(CURRICULUM_ID, this.curriculum.getUid());
		return null;
	}
	
	private void orderEducation() {
		Collections.sort(education);
	}
	
	private void orderLanguages() {
		Collections.sort(languages);
	}

	private void orderJobExperiencesByDate() {
		Collections.sort(jobExperiences);
	}
	
	private void orderSkills() {
		Collections.sort(skills);
	}
}