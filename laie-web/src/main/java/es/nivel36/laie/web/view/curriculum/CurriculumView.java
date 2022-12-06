package es.nivel36.laie.web.view.curriculum;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import org.omnifaces.cdi.Param;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.curriculum.Curriculum;
import es.nivel36.laie.ejb.curriculum.CurriculumService;
import es.nivel36.laie.ejb.curriculum.Education;
import es.nivel36.laie.ejb.curriculum.JobExperience;
import es.nivel36.laie.ejb.curriculum.Language;
import es.nivel36.laie.ejb.curriculum.LanguageLevel;
import es.nivel36.laie.ejb.curriculum.Skill;
import es.nivel36.laie.web.core.IllegalPageStateException;
import es.nivel36.laie.web.core.view.AbstractView;
import es.nivel36.laie.web.view.candidate.EditCandidatePermission;
import jakarta.annotation.PostConstruct;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class CurriculumView extends AbstractView {

	private static final long serialVersionUID = -4824952921251852587L;

	private @Param Candidate candidate;

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

	private List<Month> months;
	
	private transient @Inject CurriculumService curriculumService;
	
	private transient @Inject EditCandidatePermission editCandidatePermission;

	@PostConstruct
	public void init() {
		if (this.candidate == null) {
			throw new IllegalPageStateException();
		}
		this.chekEditPermission();
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

		this.months = this.buildMonthsCombo();
		this.years = this.buildYearsCombo(50, 0);
	}

	private void chekEditPermission() {
		if(!editCandidatePermission.validate(candidate)) {
			throw new SecurityException();
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

	public List<Month> getMonths() {
		return this.months;
	}

	///////////////////////////////////////////////////////////////////////////
	// JOB EXPERIENCES
	///////////////////////////////////////////////////////////////////////////

	public void addJobExperience() {
		final JobExperience jobExperience = new JobExperience();
		this.curriculum.addJobExperience(jobExperience);
		this.jobExperiences.add(0, jobExperience);
		this.editJobExperienceIndex = 0;
	}

	public void updateJobExperience() {
		this.curriculum = this.curriculumService.updateCurriculum(curriculum);
		Collections.sort(jobExperiences);
		this.editJobExperienceIndex = null;
	}

	public void deleteJobExperience(final JobExperience jobExperience) {
		this.jobExperiences.remove(jobExperience);
		this.curriculum.getJobExperiences().remove(jobExperience);
		if (!jobExperience.isNew()) {
			this.curriculum = this.curriculumService.updateCurriculum(curriculum);
		}
		this.editJobExperienceIndex = null;
	}

	public void editJobExperience(final int index) {
		this.editJobExperienceIndex = Integer.valueOf(index);
	}
	
	public void cancelEditJobExperience(final JobExperience jobExperience) {
		this.editJobExperienceIndex = null;
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
		this.education.add(0, education);
		this.editEducationIndex = 0;
	}

	public void updateEducation() {
		this.curriculum = this.curriculumService.updateCurriculum(curriculum);
		Collections.sort(this.education);
		this.editEducationIndex = null;
	}

	public void deleteEducation(final Education education) {
		this.education.remove(education);
		this.curriculum.getEducation().remove(education);
		if (!education.isNew()) {
			this.curriculum = this.curriculumService.updateCurriculum(curriculum);
		}
		this.editEducationIndex = null;
	}

	public void editEducation(final int index) {
		this.editEducationIndex = Integer.valueOf(index);
	}
	
	public void cancelEditEducation() {
		this.editEducationIndex = null;
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
		this.languages.add(0, language);
		this.editLanguageIndex = 0;
	}

	public void updateLanguage() {
		this.curriculum = this.curriculumService.updateCurriculum(curriculum);
		this.editLanguageIndex = null;
	}

	public void deleteLanguage(final Language language) {
		this.languages.remove(language);
		this.curriculum.getLanguages().remove(language);
		if (!language.isNew()) {
			this.curriculum = this.curriculumService.updateCurriculum(curriculum);
		}
		this.editLanguageIndex = null;
	}

	public void editLanguage(final int index) {
		this.editLanguageIndex = Integer.valueOf(index);
	}
	
	public void cancelEditLanguage() {
		this.editLanguageIndex = null;
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
	
	public void cancelEditSkills() {
		this.editSkills = false;
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

	///////////////////////////////////////////////////////////////////////////
	// COMBOS
	///////////////////////////////////////////////////////////////////////////

	public static class Month {

		private String monthName;

		private Integer monthNumber;

		public String getMonthName() {
			return this.monthName;
		}

		public Integer getMonthNumber() {
			return this.monthNumber;
		}

		public void setMonthName(final String monthName) {
			this.monthName = monthName;
		}

		public void setMonthNumber(final Integer monthNumber) {
			this.monthNumber = monthNumber;
		}
	}

	private static final String FILE_NAME = "es.nivel36.laie.i18n";

	private List<Month> buildMonthsCombo() {
		final List<Month> months = new ArrayList<>(12);
		for (int i = 1; i < 13; i++) {
			final Month newMonth = new Month();
			newMonth.setMonthName(this.message("date.month." + i));
			newMonth.setMonthNumber(i);
			months.add(newMonth);
		}
		return months;
	}

	private List<Integer> buildYearsCombo(final int minusYear, final int plusYear) {
		final int presentYear = LocalDate.now().getYear();
		final int range = (plusYear + minusYear);
		final int maxYear = presentYear + plusYear;
		final List<Integer> years = new ArrayList<>();
		for (int i = 0; i < range; i++) {
			years.add(maxYear - i);
		}
		return years;
	}

	private Locale getLocale() {
		final UIViewRoot uIViewRoot = this.facesContext.getViewRoot();
		final Locale locale;
		if (uIViewRoot != null) {
			locale = uIViewRoot.getLocale();
		} else {
			locale = Locale.ENGLISH;
		}
		return locale;
	}

	private ResourceBundle getResourceBundle(final String filename) {
		final Locale locale = this.getLocale();
		return ResourceBundle.getBundle(filename, locale);
	}

	private String message(final String message) {
		final ResourceBundle bundle = this.getResourceBundle(FILE_NAME);
		return bundle.getString(message);
	}
	
	////////////////////////////////////////////////////////////////////////////
	// SET
	////////////////////////////////////////////////////////////////////////////
	
	public void setCurriculumService(final CurriculumService curriculumService) {
		Objects.requireNonNull(curriculumService);
		this.curriculumService = curriculumService;
	}

	public void setEditCandidatePermission(final EditCandidatePermission editCandidatePermission) {
		Objects.requireNonNull(editCandidatePermission);
		this.editCandidatePermission = editCandidatePermission;
	}
}