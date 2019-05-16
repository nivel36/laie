package ged.ejb.curriculum;

import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.model.AbstractEntity;

@Entity
public class Curriculum extends AbstractEntity {

	private static final long serialVersionUID = 5171402772798965261L;

	@NotNull
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "curriculum")
	private Candidate candidate;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "curriculum", orphanRemoval = true)
	private Set<Education> education;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "curriculum", orphanRemoval = true)
	private Set<JobExperience> jobExperiences;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "curriculum", orphanRemoval = true)
	private Set<Language> languages;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "curriculum", orphanRemoval = true)
	private Set<Skill> skills;

	public void addEducation(final Education education) {
		education.setCurriculum(this);
		this.education.add(education);
	}

	public void addJobExperience(final JobExperience jobExperience) {
		jobExperience.setCurriculum(this);
		this.jobExperiences.add(jobExperience);
	}

	public void addLanguage(final Language language) {
		language.setCurriculum(this);
		this.languages.add(language);
	}

	public void addSkill(final Skill skill) {
		skill.setCurriculum(this);
		this.skills.add(skill);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Curriculum other = (Curriculum) obj;
		return Objects.equals(this.candidate, other.candidate);
	}

	public Candidate getCandidate() {
		return this.candidate;
	}

	public Set<Education> getEducation() {
		return this.education;
	}

	public int getEducationSize() {
		return this.education.size();
	}

	public Set<JobExperience> getJobExperiences() {
		return this.jobExperiences;
	}

	public int getJobExperiencesSize() {
		return this.jobExperiences.size();
	}

	public Set<Language> getLanguages() {
		return this.languages;
	}

	public int getLanguagesSize() {
		return this.languages.size();
	}

	public Set<Skill> getSkills() {
		return this.skills;
	}

	public int getSkillsSize() {
		return this.skills.size();
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.candidate);
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

	public void setCandidate(final Candidate candidate) {
		this.candidate = candidate;
	}

	public void setEducation(final Set<Education> education) {
		this.education = education;
	}

	public void setJobExperiences(final Set<JobExperience> jobExperiences) {
		this.jobExperiences = jobExperiences;
	}

	public void setLanguages(final Set<Language> languages) {
		this.languages = languages;
	}

	public void setSkills(final Set<Skill> skills) {
		this.skills = skills;
	}

	@Override
	public String toString() {
		return "Curriculum [education=" + this.education + ", jobExperiences=" + this.jobExperiences + ", languages="
				+ this.languages + ", skills=" + this.skills + "]";
	}
}
