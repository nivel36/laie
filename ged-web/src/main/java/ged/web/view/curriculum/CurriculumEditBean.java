package ged.web.view.curriculum;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.curriculum.Curriculum;
import ged.ejb.curriculum.CurriculumService;
import ged.ejb.curriculum.education.Education;
import ged.ejb.curriculum.jobexperience.JobExperience;
import ged.ejb.curriculum.language.Language;
import ged.ejb.curriculum.skills.Skill;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class CurriculumEditBean extends AbstractBean {

	private static final String CURRICULUM_KEY = "curriculum";

	private static final long serialVersionUID = -7941713518794892268L;

	private Curriculum curriculum;

	@Inject
	private transient CurriculumService curriculumService;

	private List<Education> education = new ArrayList<>();

	private List<JobExperience> jobExperiences = new ArrayList<>();

	private List<Language> languages = new ArrayList<>();

	private List<Skill> skills = new ArrayList<>();

	public void addEducation() {
		final Education newEducation = new Education();
		newEducation.setCurriculum(this.curriculum);
		this.education.add(newEducation);
	}

	public void addJobExperience() {
		final JobExperience jobExperience = new JobExperience();
		jobExperience.setCurriculum(this.curriculum);
		this.jobExperiences.add(jobExperience);
	}

	public void addLanguage() {
		final Language language = new Language();
		language.setCurriculum(this.curriculum);
		this.languages.add(language);
	}

	public void addSkill() {
		final Skill skill = new Skill();
		skill.setCurriculum(this.curriculum);
		this.skills.add(skill);
	}

	public String cancel() {
		return "/candidate/candidate?faces-redirect=true&candidateId=" + this.curriculum.getCandidate().getId();
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

	public List<Skill> getSkills() {
		return this.skills;
	}

	@PostConstruct
	public void init() {
		if (this.flash.containsKey(CURRICULUM_KEY)) {
			this.curriculum = (Curriculum) this.flash.get(CURRICULUM_KEY);
		}
		else {
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

		this.skills.addAll(this.curriculum.getSkills());
		this.education.addAll(this.curriculum.getEducation());
		this.jobExperiences.addAll(this.curriculum.getJobExperiences());
		this.languages.addAll(this.curriculum.getLanguages());

		this.flash.put(CURRICULUM_KEY, this.curriculum);
		this.flash.put("candidate", this.curriculum.getCandidate());
	}

	private <E> Set<E> listToSet(final List<E> list) {
		return new HashSet<>(list);
	}

	public void removeEducation(final Education education) {
		this.education.remove(education);
	}

	public void removeJobExperience(final JobExperience jobExperience) {
		this.jobExperiences.remove(jobExperience);
	}

	public void removeLanguage(final Language language) {
		this.languages.remove(language);
	}

	public void removeSkill(final Skill skill) {
		this.skills.remove(skill);
	}

	public String save() {
		this.curriculum.setEducation(this.listToSet(this.education));
		this.curriculum.setLanguages(this.listToSet(this.languages));
		this.curriculum.setJobExperiences(this.listToSet(this.jobExperiences));
		this.curriculum.setSkills(this.listToSet(this.skills));
		this.curriculum = this.curriculumService.save(this.curriculum);
		return "/candidate/candidate?faces-redirect=true&candidateId=" + this.curriculum.getCandidate().getId();
	}

	public void setCurriculum(final Curriculum curriculum) {
		this.curriculum = curriculum;
	}

	public void setCurriculumService(final CurriculumService curriculumService) {
		this.curriculumService = curriculumService;
	}

	public void setEducation(final List<Education> education) {
		this.education = education;
	}

	public void setJobExperiences(final List<JobExperience> jobExperiences) {
		this.jobExperiences = jobExperiences;
	}

	public void setLanguages(final List<Language> languages) {
		this.languages = languages;
	}

	public void setSkills(final List<Skill> skills) {
		this.skills = skills;
	}
}
