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
import org.primefaces.event.SelectEvent;

import ged.ejb.candidate.Candidate;
import ged.ejb.curriculum.Curriculum;
import ged.ejb.curriculum.CurriculumService;
import ged.ejb.curriculum.Education;
import ged.ejb.curriculum.Language;
import ged.ejb.curriculum.Skill;
import ged.ejb.curriculum.jobexperience.JobExperience;
import ged.ejb.curriculum.jobexperience.JobExperienceService;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class CurriculumBean extends AbstractBean {

	private static final long serialVersionUID = -5942086439519787220L;

	@SuppressWarnings("cdi-ambiguous-dependency")
	@Inject
	@Param(name = "candidateId", required = true)
	private Candidate candidate;

	private Curriculum curriculum;

	@Inject
	private transient CurriculumService curriculumService;

	private final List<Education> education = new ArrayList<>();

	private List<JobExperience> jobExperiences = new ArrayList<>();

	@Inject
	private transient JobExperienceService jobExperienceService;

	private final List<Language> languages = new ArrayList<>();

	private final List<Skill> skills = new ArrayList<>();

	public void editJobExperience(final JobExperience jobExperience) {
		this.openDialog("jobExperienceDialog", this.buildDialogParameter("jobExperienceId", String.valueOf(jobExperience.getId())));
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

	public List<Skill> getSkills() {
		return this.skills;
	}

	@PostConstruct
	public void init() {
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
		this.skills.addAll(this.curriculum.getSkills());
		this.education.addAll(this.curriculum.getEducation());
		this.jobExperiences.addAll(this.curriculum.getJobExperiences());
		this.languages.addAll(this.curriculum.getLanguages());
		this.orderJobExperiencesByDate();
	}

	public void newJobExperience() {
		this.openDialog("jobExperienceDialog", this.buildDialogParameter("curriculumId", String.valueOf(this.curriculum.getId())));
	}

	public void onEditJobExperience(final SelectEvent event) {
		this.jobExperiences = this.jobExperienceService.findByCurriculum(this.curriculum);
		this.orderJobExperiencesByDate();
	}

	public void orderJobExperiencesByDate() {
		this.jobExperiences.sort(Comparator.comparing(JobExperience::getStartYear).reversed());
	}

	public void setCurriculumService(final CurriculumService curriculumService) {
		this.curriculumService = curriculumService;
	}
}