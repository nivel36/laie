package es.nivel36.laie.ejb.curriculum;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import es.nivel36.laie.ejb.curriculum.education.EducationDto;
import es.nivel36.laie.ejb.curriculum.jobexperience.JobExperienceDto;
import es.nivel36.laie.ejb.curriculum.language.LanguageDto;

public class CurriculumDto implements Serializable {

	private static final long serialVersionUID = -331034302672944386L;
	
	private Set<EducationDto> education;

	private Set<JobExperienceDto> jobExperiences;

	private Set<LanguageDto> languages;

	private Set<String> skills;

	private String uid;

	public Set<EducationDto> getEducation() {
		return this.education;
	}

	public int getEducationSize() {
		return this.education.size();
	}

	public Set<JobExperienceDto> getJobExperiences() {
		return this.jobExperiences;
	}

	public int getJobExperiencesSize() {
		return this.jobExperiences.size();
	}

	public Set<LanguageDto> getLanguages() {
		return this.languages;
	}

	public int getLanguagesSize() {
		return this.languages.size();
	}

	public Set<String> getSkills() {
		return this.skills;
	}

	public String getUid() {
		return uid;
	}

	public void setEducation(final Set<EducationDto> education) {
		this.education = education;
	}

	public void setJobExperiences(final Set<JobExperienceDto> jobExperiences) {
		this.jobExperiences = jobExperiences;
	}

	public void setLanguages(final Set<LanguageDto> languages) {
		this.languages = languages;
	}

	public void setSkills(final List<String> skills) {
		this.skills = new HashSet<>(skills);
	}

	public void setSkills(final Set<String> skills) {
		this.skills = skills;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		return "Curriculum [education=" + this.education + ", jobExperiences=" + this.jobExperiences + ", languages="
				+ this.languages + ", skills=" + this.skills + "]";
	}

}
