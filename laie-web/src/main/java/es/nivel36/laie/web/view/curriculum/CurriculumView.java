package es.nivel36.laie.web.view.curriculum;

import java.time.LocalDateTime;
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

	private Integer editEducationIndex;

	private Integer editJobExperienceIndex;

	private boolean editSkills;

	private List<Integer> years;

	@Inject
	private CurriculumService curriculumService;

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
		initYears();
	}

	private void initYears() {
		this.years = new ArrayList<>();
		final int endYear = LocalDateTime.now().getYear();
		final int startYear = endYear - 50;
		for (int i = startYear; i < endYear; i++) {
			years.add(Integer.valueOf(i));
		}
	}

	public Candidate getCandidate() {
		return this.candidate;
	}

	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	public List<Integer> getYears() {
		return this.years;
	}

	///////////////////////////////////////////////////////////////////////////
	// JOB EXPERIENCES
	///////////////////////////////////////////////////////////////////////////

	public void addJobExperience() {
		final JobExperience jobExperience = new JobExperience();
		this.curriculum.addJobExperience(jobExperience);
		this.jobExperiences.add(jobExperience);
		this.editJobExperienceIndex = this.jobExperiences.size() - 1;
	}

	public void updateJobExperience() {
		this.curriculum = this.curriculumService.updateCurriculum(curriculum);
		this.editJobExperienceIndex = null;
	}

	public void deleteJobExperience(final JobExperience jobExperience) {
		this.jobExperiences.remove(jobExperience);
		this.curriculum.getJobExperiences().remove(jobExperience);
		this.curriculum = this.curriculumService.updateCurriculum(curriculum);
	}

	public void editJobExperience(final int index) {
		this.editJobExperienceIndex = Integer.valueOf(index);
	}

	public List<JobExperience> getJobExperiences() {
		return this.jobExperiences;
	}

	public Integer getEditJobExperienceIndex() {
		return editJobExperienceIndex;
	}

	///////////////////////////////////////////////////////////////////////////
	// EDUCATION
	///////////////////////////////////////////////////////////////////////////

	public void addEducation() {
		final Education education = new Education();
		this.curriculum.addEducation(education);
		this.education.add(education);
		this.editEducationIndex = this.education.size() - 1;
	}

	public void updateEducation() {
		this.curriculum = this.curriculumService.updateCurriculum(curriculum);
		this.editEducationIndex = null;
	}

	public void deleteEducation(final Education education) {
		this.education.remove(education);
		this.curriculum.getEducation().remove(education);
		this.curriculum = this.curriculumService.updateCurriculum(curriculum);
		this.editEducationIndex = null;
	}

	public void editEducation(final int index) {
		this.editEducationIndex = Integer.valueOf(index);
	}

	public Integer getEditEducationIndex() {
		return editEducationIndex;
	}

	public List<Education> getEducation() {
		return this.education;
	}

	///////////////////////////////////////////////////////////////////////////
	// LANGUAGE
	///////////////////////////////////////////////////////////////////////////

	public void addLanguage() {
		final Language language = new Language();
		this.curriculum.addLanguage(language);
		this.languages.add(language);
		this.editLanguageIndex = this.languages.size() - 1;
	}

	public void updateLanguage() {
		this.curriculum = this.curriculumService.updateCurriculum(curriculum);
		this.editLanguageIndex = null;
	}

	public void deleteLanguage(final Language language) {
		this.languages.remove(language);
		this.curriculum.getLanguages().remove(language);
		this.curriculum = this.curriculumService.updateCurriculum(curriculum);
	}

	public void editLanguage(final int index) {
		this.editLanguageIndex = Integer.valueOf(index);
	}

	public Integer getEditLanguageIndex() {
		return editLanguageIndex;
	}

	public List<String> getLanguageLevels() {
		return languageLevels;
	}

	public List<Language> getLanguages() {
		return this.languages;
	}

	///////////////////////////////////////////////////////////////////////////
	// SKILLS
	///////////////////////////////////////////////////////////////////////////

	public void updateSkills() {
		this.curriculum.setSkills(mapStringToSkills());
		this.curriculum = this.curriculumService.updateCurriculum(curriculum);
		this.editSkills = false;
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

	public void editSkill() {
		this.editSkills = true;
	}

	public boolean isEditSkills() {
		return editSkills;
	}

	public List<String> getSkills() {
		return this.skills;
	}

	public void setSkills(List<String> skills) {
		this.skills = skills;
	}
}