package es.nivel36.laie.web.view.curriculum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.candidate.CandidateService;
import es.nivel36.laie.ejb.curriculum.Curriculum;
import es.nivel36.laie.ejb.curriculum.CurriculumService;
import es.nivel36.laie.ejb.curriculum.education.Education;
import es.nivel36.laie.ejb.curriculum.jobexperience.JobExperience;
import es.nivel36.laie.ejb.curriculum.language.Language;
import es.nivel36.laie.web.core.IllegalPageStateException;
import es.nivel36.laie.web.core.view.AbstractView;

@Named
@ViewScoped
public class CurriculumView extends AbstractView {

	private static final long serialVersionUID = -4824952921251852587L;

	private Candidate candidate;

	private Curriculum curriculum;

	private List<Education> education;

	private List<JobExperience> jobExperiences;

	private List<Language> languages;

	private List<String> skills;

	@Inject
	private CandidateService candidateService;

	@Inject
	private CurriculumService cuirriculumService;

	@PostConstruct
	public void init() {
		final String candidateId = this.getValueFromGetParameters("candidate", true);
		this.candidate = this.candidateService.findCandidateById(candidateId);
		if (this.candidate == null) {
			throw new IllegalPageStateException();
		}
		this.curriculum = this.cuirriculumService.findCandidatesCurriculum(candidateId);
		if (this.curriculum == null) {
			this.curriculum = new Curriculum();
		}
		if (this.curriculum.getSkills() == null) {
			this.curriculum.setSkills(new HashSet<String>());
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
		for (final String skill : this.curriculum.getSkills()) {
			this.skills.add(skill);
		}
		this.education = new ArrayList<>(this.curriculum.getEducation());
		this.jobExperiences = new ArrayList<>(this.curriculum.getJobExperiences());
		this.languages = new ArrayList<>(this.curriculum.getLanguages());
		this.orderJobExperiencesByDate();
		this.orderEducation();
		this.orderLanguages();
		this.orderSkills();
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