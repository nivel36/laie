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

import ged.ejb.curriculum.Curriculum;
import ged.ejb.curriculum.CurriculumService;
import ged.ejb.curriculum.Education;
import ged.ejb.curriculum.JobExperience;
import ged.ejb.curriculum.Language;
import ged.ejb.curriculum.Skill;
import ged.web.core.view.AbstractPageBean;

@Named
@ViewScoped
public class CurriculumEditBean extends AbstractPageBean {

	private static final long serialVersionUID = -7941713518794892268L;

	private Curriculum curriculum;

	@Inject
	private transient CurriculumService curriculumService;

	private List<Education> education = new ArrayList<Education>();

	private List<JobExperience> jobExperiences = new ArrayList<JobExperience>();

	private List<Language> languages = new ArrayList<Language>();

	private List<Skill> skills = new ArrayList<Skill>();

	public void addEducation() {
		final Education education = new Education();
		education.setCurriculum(this.curriculum);
		this.education.add(education);
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
		return "candidateSearch?faces-redirect=true";
	}

	private void error() {
		final NavigationHandler navigationHandler = this.facesContext.getApplication().getNavigationHandler();
		navigationHandler.handleNavigation(this.facesContext, null, "candidateSearch?faces-redirect=true");
		this.facesContext.renderResponse();
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
		if (this.flash.containsKey("curriculum")) {
			this.curriculum = (Curriculum) this.flash.get("curriculum");
		} else {
			error();
			return;
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

		this.flash.put("curriculum", this.curriculum);
		this.flash.put("candidate", this.curriculum.getCandidate());
	}

	private void insertOrUpdate(final Curriculum curriculum) {
		if (curriculum.getId() == 0) {
			this.curriculumService.insertCurriculum(curriculum);
		} else {
			this.curriculum = this.curriculumService.updateCurriculum(curriculum);
		}
	}

	private <E> Set<E> listToSet(final List<E> list) {
		return new HashSet<E>(list);
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
		this.curriculum.setEducation(listToSet(this.education));
		this.curriculum.setLanguages(listToSet(this.languages));
		this.curriculum.setJobExperiences(listToSet(this.jobExperiences));
		this.curriculum.setSkills(listToSet(this.skills));
		insertOrUpdate(this.curriculum);
		return "candidateSearch?faces-redirect=true";
	}

	public void setCurriculum(final Curriculum curriculum) {
		this.curriculum = curriculum;
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
