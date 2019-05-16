package ged.web.view.curriculum;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;

import ged.ejb.candidate.Candidate;
import ged.ejb.curriculum.Curriculum;
import ged.ejb.curriculum.CurriculumService;
import ged.ejb.curriculum.Education;
import ged.ejb.curriculum.JobExperience;
import ged.ejb.curriculum.Language;
import ged.ejb.curriculum.Skill;
import ged.web.core.IllegalPageStateException;
import ged.web.core.util.PageEnum;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class CurriculumBean extends AbstractBean {

	private static final String CURRICULUM_KEY = "curriculum";

	private static final long serialVersionUID = -5942086439519787220L;

	@Inject
	@Param(name = "id", required = true)
	private Candidate candidate;

	private Curriculum curriculum;

	@Inject
	private transient CurriculumService curriculumService;

	private List<Education> education;

	private List<JobExperience> jobExperiences;

	private List<Language> languages;

	private List<String> skills;

	public String editEducation(final Education education) {
		this.putValueToFlash("education", education);
		this.putValueToFlash(CURRICULUM_KEY, curriculum);
		return PageEnum.CURRICULUM_EDUCATION.getRedirectUrl();
	}

	public String editJobExperience(final JobExperience jobExperience) {
		this.putValueToFlash("jobExperience", jobExperience);
		this.putValueToFlash(CURRICULUM_KEY, curriculum);
		return PageEnum.CURRICULUM_JOB_EXPERIENCE.getRedirectUrl();
	}

	public String editLanguages() {
		this.putValueToFlash(CURRICULUM_KEY, curriculum);
		return PageEnum.CURRICULUM_LANGUAGE.getRedirectUrl();
	}
	
	public String editSkills() {
		this.putValueToFlash(CURRICULUM_KEY, curriculum);
		return PageEnum.CURRICULUM_SKILLS.getRedirectUrl();
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
		if (this.candidate == null) {
			throw new IllegalPageStateException();
		}
		this.curriculum = this.curriculumService.findByCandidate(this.candidate);
		if (this.curriculum == null) {
			this.curriculum = new Curriculum();
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
		this.orderEducationByDate();
	}

	public String newEducation() {
		this.putValueToFlash(CURRICULUM_KEY, this.curriculum);
		return PageEnum.CURRICULUM_EDUCATION.getRedirectUrl();
	}

	public String newJobExperience() {
		this.putValueToFlash(CURRICULUM_KEY, this.curriculum);
		return PageEnum.CURRICULUM_JOB_EXPERIENCE.getRedirectUrl();
	}

	public String newLanguage() {
		this.putValueToFlash(CURRICULUM_KEY, this.curriculum);
		return PageEnum.CURRICULUM_LANGUAGE.getRedirectUrl();
	}

	public String newSkill() {
		this.putValueToFlash(CURRICULUM_KEY, this.curriculum);
		return PageEnum.CURRICULUM_SKILLS.getRedirectUrl();
	}

	private void orderEducationByDate() {
		this.education.sort(Comparator.comparing(Education::getStartYear).reversed());
	}

	private void orderJobExperiencesByDate() {
		this.jobExperiences.sort(Comparator.comparing(JobExperience::getStartDate).reversed());
	}

	public void setCurriculumService(final CurriculumService curriculumService) {
		this.curriculumService = curriculumService;
	}
}