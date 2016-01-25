package ged.web.view.candidate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.NavigationHandler;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.service.curriculum.Curriculum;
import ged.ejb.service.curriculum.CurriculumService;
import ged.ejb.service.curriculum.Education;
import ged.ejb.service.curriculum.JobExperience;
import ged.ejb.service.curriculum.Language;
import ged.ejb.service.curriculum.Skill;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class CurriculumEditBean extends AbstractBean {

	private static final long serialVersionUID = -7941713518794892268L;

	private Curriculum curriculum;

	private List<Education> education = new ArrayList<Education>();

	private List<Skill> skills = new ArrayList<Skill>();

	private List<JobExperience> jobExperiences = new ArrayList<JobExperience>();

	private List<Language> languages = new ArrayList<Language>();

	@Inject
	private CurriculumService curriculumService;

	// ////////////////////////////////////////////////////////////////////////
	// INIT
	// ////////////////////////////////////////////////////////////////////////

	@PostConstruct
	public void init() {
		if (flash.containsKey("curriculum")) {
			curriculum = (Curriculum) flash.get("curriculum");
		} else {
			error();
			return;
		}
		if (curriculum.getSkills() == null) {
			curriculum.setSkills(new HashSet<Skill>());
		}
		if (curriculum.getEducation() == null) {
			curriculum.setEducation(new HashSet<Education>());
		}
		if (curriculum.getJobExperiences() == null) {
			curriculum.setJobExperiences(new HashSet<JobExperience>());
		}
		if (curriculum.getLanguages() == null) {
			curriculum.setLanguages(new HashSet<Language>());
		}

		skills.addAll(curriculum.getSkills());
		education.addAll(curriculum.getEducation());
		jobExperiences.addAll(curriculum.getJobExperiences());
		languages.addAll(curriculum.getLanguages());

		flash.put("curriculum", curriculum);
		flash.put("candidate", curriculum.getCandidate());
	}

	private void error() {
		NavigationHandler navigationHandler = facesContext.getApplication()
				.getNavigationHandler();
		navigationHandler.handleNavigation(facesContext, null,
				"candidateSearch?faces-redirect=true");
		facesContext.renderResponse();
	}

	// ////////////////////////////////////////////////////////////////////////
	// SET AND GETS
	// ////////////////////////////////////////////////////////////////////////

	public Curriculum getCurriculum() {
		return curriculum;
	}

	public void setCurriculum(Curriculum curriculum) {
		this.curriculum = curriculum;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

	public List<Education> getEducation() {
		return education;
	}

	public void setEducation(List<Education> education) {
		this.education = education;
	}

	public List<JobExperience> getJobExperiences() {
		return jobExperiences;
	}

	public void setJobExperiences(List<JobExperience> jobExperiences) {
		this.jobExperiences = jobExperiences;
	}

	public List<Language> getLanguages() {
		return languages;
	}

	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}

	// ////////////////////////////////////////////////////////////////////////
	// ACTIONS
	// ////////////////////////////////////////////////////////////////////////

	public void addEducation() {
		Education education = new Education();
		education.setCurriculum(curriculum);
		this.education.add(education);
	}

	public void removeEducation(Education education) {
		this.education.remove(education);
	}

	public void addJobExperience() {
		JobExperience jobExperience = new JobExperience();
		jobExperience.setCurriculum(curriculum);
		this.jobExperiences.add(jobExperience);
	}

	public void removeJobExperience(JobExperience jobExperience) {
		this.jobExperiences.remove(jobExperience);
	}

	public void addLanguage() {
		Language language = new Language();
		language.setCurriculum(curriculum);
		this.languages.add(language);
	}

	public void removeLanguage(Language language) {
		this.languages.remove(language);
	}

	public void addSkill() {
		Skill skill = new Skill();
		skill.setCurriculum(curriculum);
		skills.add(skill);
	}

	public void removeSkill(Skill skill) {
		skills.remove(skill);
	}

	public String save() {
		curriculum.setEducation(listToSet(education));
		curriculum.setLanguages(listToSet(languages));
		curriculum.setJobExperiences(listToSet(jobExperiences));
		curriculum.setSkills(listToSet(skills));
		curriculumService.insertOrUpdate(curriculum);
		return "candidateSearch?faces-redirect=true";
	}

	private <E> Set<E> listToSet(List<E> list) {
		return new HashSet<E>(list);
	}

	public String cancel() {
		return "candidateSearch?faces-redirect=true";
	}
}
