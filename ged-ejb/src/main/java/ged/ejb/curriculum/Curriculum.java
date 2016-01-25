package ged.ejb.curriculum;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.AbstractEntity;

@Entity
@NamedQueries({ @NamedQuery(name = "Curriculum.getByCandidateId", query = "SELECT c FROM Curriculum c LEFT JOIN FETCH c.candidate LEFT JOIN FETCH c.education LEFT JOIN FETCH c.jobExperiences LEFT JOIN FETCH c.languages LEFT JOIN FETCH c.skills WHERE c.candidate.id = :candidateId") })
public class Curriculum extends AbstractEntity {

	private static final long serialVersionUID = 5171402772798965261L;

	@NotNull
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "curriculum")
	private Candidate candidate;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "curriculum", orphanRemoval = true)
	private Set<Education> education = new HashSet<Education>();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "curriculum", orphanRemoval = true)
	private Set<JobExperience> jobExperiences = new HashSet<JobExperience>();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "curriculum", orphanRemoval = true)
	private Set<Language> languages = new HashSet<Language>();

	@Column(length = 256)
	private String perfilProfesional;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "curriculum", orphanRemoval = true)
	private Set<Skill> skills = new HashSet<Skill>();

	public void addEducation(Education education) {
		education.setCurriculum(this);
		this.education.add(education);
	}

	public void addJobExperience(JobExperience jobExperience) {
		jobExperience.setCurriculum(this);
		this.jobExperiences.add(jobExperience);
	}

	public void addLanguage(Language language) {
		language.setCurriculum(this);
		this.languages.add(language);
	}

	public void addSkill(Skill skill) {
		skill.setCurriculum(this);
		this.skills.add(skill);
	}

	public void removeEducation(Education education) {
		this.education.remove(education);
	}

	public void removeJobExperience(JobExperience jobExperience) {
		this.jobExperiences.remove(jobExperience);
	}

	public void removeLanguage(Language language) {
		this.languages.remove(language);
	}

	public void removeSkill(Skill skill) {
		this.skills.remove(skill);
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public Set<Education> getEducation() {
		return education;
	}

	public int getEducationSize() {
		return education.size();
	}

	public Set<JobExperience> getJobExperiences() {
		return jobExperiences;
	}

	public int getJobExperiencesSize() {
		return jobExperiences.size();
	}

	public Set<Language> getLanguages() {
		return languages;
	}

	public int getLanguagesSize() {
		return languages.size();
	}

	public String getPerfilProfesional() {
		return perfilProfesional;
	}

	public Set<Skill> getSkills() {
		return skills;
	}

	public int getSkillsSize() {
		return skills.size();
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public void setEducation(Set<Education> education) {
		this.education = education;
	}

	public void setJobExperiences(Set<JobExperience> jobExperiences) {
		this.jobExperiences = jobExperiences;
	}

	public void setLanguages(Set<Language> languages) {
		this.languages = languages;
	}

	public void setPerfilProfesional(String perfilProfesional) {
		this.perfilProfesional = perfilProfesional;
	}

	public void setSkills(Set<Skill> skills) {
		this.skills = skills;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Curriculum other = (Curriculum) obj;
		if (candidate == null) {
			if (other.candidate != null)
				return false;
		} else if (!candidate.equals(other.candidate))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((candidate == null) ? 0 : candidate.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Curriculum [education=" + education + ", jobExperiences="
				+ jobExperiences + ", languages=" + languages + ", skills="
				+ skills + "]";
	}
}
