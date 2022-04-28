package es.nivel36.laie.web.view.curriculum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
import es.nivel36.laie.ejb.curriculum.language.LanguageLevel;
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

	private List<String> skills;

	private List<String> languageLevels;

	private Integer editLanguageIndex;

	private boolean editSkills;

	@Inject
	private CurriculumService curriculumService;

	public void deleteLanguage(final Language language) {
		this.curriculum.getLanguages().remove(language);
		this.curriculum = this.curriculumService.updateCurriculum(curriculum);
	}

	public void editLanguage(final int index) {
		editLanguageIndex = Integer.valueOf(index);
	}

	public void editSkill() {
		this.editSkills = true;
	}

	public Candidate getCandidate() {
		return this.candidate;
	}

	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	public Integer getEditLanguageIndex() {
		return editLanguageIndex;
	}

	public List<Education> getEducation() {
		return this.education;
	}

	public List<JobExperience> getJobExperiences() {
		return this.jobExperiences;
	}

	public List<String> getLanguageLevels() {
		return languageLevels;
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
		this.curriculum = this.curriculumService.findCandidatesCurriculum(candidate);
		if (this.curriculum == null) {
			this.curriculum = new Curriculum();
			this.curriculum.setCandidate(this.candidate);
		}
		if (this.curriculum.getSkills() == null) {
			this.curriculum.setSkills(new HashSet<Skill>());
			this.skills = new ArrayList<>();
		} else {
			this.skills = mapSkillsToString();
			Collections.sort(skills);
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
		this.languageLevels = new ArrayList<String>();
		for (LanguageLevel languageLevel : LanguageLevel.values()) {
			this.languageLevels.add(languageLevel.name());
		}
		Collections.sort(jobExperiences);
		Collections.sort(education);
		Collections.sort(languages);
	}
	
	public void addLanguage() {
		this.languages.add(new Language());
	}

	public boolean isEditSkills() {
		return editSkills;
	}

	private List<String> mapSkillsToString() {
		final Set<Skill> skills = this.getCurriculum().getSkills();
		if (skills == null) {
			return new ArrayList<>();
		}
		return skills.stream().map(Skill::getName).collect(Collectors.toList());
	}

	private Set<Skill> mapStringToSkills() {
		return skills.stream().map(Skill::new).collect(Collectors.toSet());
	}

	public void updateSkills() {
		this.curriculum.setSkills(mapStringToSkills());
		this.curriculumService.updateCurriculum(curriculum);
	}

	public void setSkills(List<String> skills) {
		this.skills = skills;
	}

	public void updateLanguage(final Language language) {
		this.curriculum = this.curriculumService.updateCurriculum(curriculum);
		this.editLanguageIndex = null;
	}
}