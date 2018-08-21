package ged.web.view.curriculum;

import static ged.ejb.core.util.Parameters.map;
import static ged.web.core.util.Navigate.to;
import static ged.web.core.util.PageEnum.CURRICULUM;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.curriculum.Curriculum;
import ged.ejb.curriculum.CurriculumService;
import ged.ejb.curriculum.education.Education;
import ged.ejb.curriculum.jobexperience.JobExperience;
import ged.ejb.curriculum.language.Language;
import ged.ejb.curriculum.skills.Skill;
import ged.web.core.view.AbstractDialogBean;

@Named
@ViewScoped
public class CurriculumDialogBean extends AbstractDialogBean {

	private static final long serialVersionUID = -8853506798337492403L;

	@Inject
	private transient CandidateService candidateService;

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

	public void cancel() {
		to(CURRICULUM).withParams(map("id", this.curriculum.getCandidate().getId())).doGet();
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
		final Long curriculumId = this.getIdFromParameters("curriculumId");
		if (curriculumId != null) {
			this.curriculum = this.curriculumService.find(curriculumId);
		}
		else {
			this.curriculum = new Curriculum();
		}

		final Long candidateId = this.getIdFromParameters("candidateId");
		final Candidate candidate = this.candidateService.find(candidateId);
		this.curriculum.setCandidate(candidate);

		if (this.curriculum.getSkills() == null) {
			this.curriculum.setSkills(new HashSet<Skill>());
		}
		this.skills.addAll(this.curriculum.getSkills());

		if (this.curriculum.getEducation() == null) {
			this.curriculum.setEducation(new HashSet<Education>());
		}
		this.education.addAll(this.curriculum.getEducation());

		if (this.curriculum.getJobExperiences() == null) {
			this.curriculum.setJobExperiences(new HashSet<JobExperience>());
		}
		this.jobExperiences.addAll(this.curriculum.getJobExperiences());

		if (this.curriculum.getLanguages() == null) {
			this.curriculum.setLanguages(new HashSet<Language>());
		}
		this.languages.addAll(this.curriculum.getLanguages());
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

	public void save() {
		this.curriculum.setEducation(this.listToSet(this.education));
		this.curriculum.setLanguages(this.listToSet(this.languages));
		this.curriculum.setJobExperiences(this.listToSet(this.jobExperiences));
		this.curriculum.setSkills(this.listToSet(this.skills));
		this.curriculumService.save(this.curriculum);
		this.closeDialog(this.curriculum);
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
