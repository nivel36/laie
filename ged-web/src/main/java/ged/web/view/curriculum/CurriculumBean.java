package ged.web.view.curriculum;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import ged.ejb.candidate.Candidate;
import ged.ejb.curriculum.Curriculum;
import ged.ejb.curriculum.CurriculumService;
import ged.ejb.curriculum.education.Education;
import ged.ejb.curriculum.jobexperience.JobExperience;
import ged.ejb.curriculum.language.Language;
import ged.ejb.curriculum.skills.Skill;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class CurriculumBean extends AbstractBean {

	private static final long serialVersionUID = -5942086439519787220L;

	private Candidate candidate;

	private Curriculum curriculum;

	@Inject
	private transient CurriculumService curriculumService;

	private List<Education> education;

	private List<JobExperience> jobExperiences;

	private List<Language> languages;

	private List<String> skills;

	public void editEducation(final Education education) {
		this.openDialog("educationDialog", buildDialogParameter("educationId", String.valueOf(education.getId())));
	}

	public void editJobExperience(final JobExperience jobExperience) {
		this.openDialog("jobExperienceDialog",
				buildDialogParameter("jobExperienceId", String.valueOf(jobExperience.getId())));
	}

	public void editLanguage(final Language language) {
		this.openDialog("languageDialog", buildDialogParameter("languageId", String.valueOf(language.getId())));
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
		this.candidate = this.getValueFromFlash("candidate");
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
		orderJobExperiencesByDate();
		orderEducationByDate();
	}

	public void newEducation() {
		this.openDialog("educationDialog",
				buildDialogParameter("curriculumId", String.valueOf(this.curriculum.getId())));
	}

	public void newJobExperience() {
		this.openDialog("jobExperienceDialog",
				buildDialogParameter("curriculumId", String.valueOf(this.curriculum.getId())));
	}

	public void newLanguage() {
		this.openDialog("languageDialog",
				buildDialogParameter("curriculumId", String.valueOf(this.curriculum.getId())));
	}

	public void newSkill() {
		this.openDialog("skillsDialog", buildDialogParameter("curriculumId", String.valueOf(this.curriculum.getId())));
	}

	public void onEditEducation(final SelectEvent event) {
		init();
	}

	public void onEditJobExperience(final SelectEvent event) {
		init();
	}

	public void onEditLanguage(final SelectEvent event) {
		init();
	}

	public void onEditSkill(final SelectEvent event) {
		init();
	}

	public void onEditSkills(final SelectEvent event) {
		init();
	}

	public void orderEducationByDate() {
		this.education.sort(Comparator.comparing(Education::getStartYear).reversed());
	}

	public void orderJobExperiencesByDate() {
		this.jobExperiences.sort(Comparator.comparing(JobExperience::getStartDate).reversed());
	}

	public void setCurriculumService(final CurriculumService curriculumService) {
		this.curriculumService = curriculumService;
	}
}