package es.nivel36.laie.ejb.curriculum;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.core.model.AbstractIndexedEntity;
import es.nivel36.laie.ejb.curriculum.education.Education;
import es.nivel36.laie.ejb.curriculum.jobexperience.JobExperience;
import es.nivel36.laie.ejb.curriculum.language.Language;
import es.nivel36.laie.ejb.curriculum.skill.Skill;

@Entity
public class Curriculum extends AbstractIndexedEntity {

	private static final long serialVersionUID = 2258938088135732207L;

	@NotNull
	@OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "candidateId", nullable = false)
	private Candidate candidate;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "curriculum_id")
	private Set<Education> education;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "curriculum_id")
	private Set<JobExperience> jobExperiences;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "curriculum_id")
	private Set<Language> languages;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "curriculum_id")
	private Set<Skill> skills;

	public void addEducation(final Education education) {
		this.education.add(education);
	}

	public void addJobExperience(final JobExperience jobExperience) {
		this.jobExperiences.add(jobExperience);
	}

	public void addLanguage(final Language language) {
		this.languages.add(language);
	}

	public void addSkill(final Skill skill) {
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

	public void setSkills(final List<Skill> skills) {
		this.skills = new HashSet<>(skills);
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
