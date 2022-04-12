package es.nivel36.laie.web.view.curriculum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.curriculum.Curriculum;
import es.nivel36.laie.ejb.curriculum.CurriculumService;
import es.nivel36.laie.ejb.curriculum.education.Education;
import es.nivel36.laie.ejb.curriculum.jobexperience.JobExperience;
import es.nivel36.laie.ejb.curriculum.language.Language;
import es.nivel36.laie.ejb.curriculum.skill.Skill;
import es.nivel36.laie.web.core.IllegalPageStateException;
import es.nivel36.laie.web.core.view.AbstractView;

@Named
@ViewScoped
public class CurriculumView extends AbstractView {

	private static final long serialVersionUID = -4824952921251852587L;

	@Param
	private Candidate candidate;

	private Curriculum curriculum;

	private List<Education> education;

	private List<JobExperience> jobExperiences;

	private List<Language> languages;

	private List<Skill> skills;

	@Inject
	private CurriculumService cuirriculumService;

	@PostConstruct
	public void init() {
		if (this.candidate == null) {
			throw new IllegalPageStateException();
		}
		this.curriculum = this.cuirriculumService.findCandidatesCurriculum(candidate);
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
		this.education = new ArrayList<>(this.curriculum.getEducation());
		this.jobExperiences = new ArrayList<>(this.curriculum.getJobExperiences());
		this.languages = new ArrayList<>(this.curriculum.getLanguages());
		this.skills = new ArrayList<Skill>(this.curriculum.getSkills());
		Collections.sort(jobExperiences);
		Collections.sort(education);
		Collections.sort(languages);
		Collections.sort(skills);
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
}