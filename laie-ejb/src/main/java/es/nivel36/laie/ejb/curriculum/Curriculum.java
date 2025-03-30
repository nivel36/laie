package es.nivel36.laie.ejb.curriculum;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.core.model.Identifiable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "CURRICULUM")
public class Curriculum implements Identifiable, Serializable {

	private static final long serialVersionUID = 2258938088135732207L;

	@Id
	@Column(name = "candidate_id")
	private long id;

	@Version
	private long version;

	@NotNull
	@OneToOne
	@MapsId
	private Candidate candidate;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "curriculum", orphanRemoval = true)
	private Set<Education> education = new HashSet<>();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "curriculum", orphanRemoval = true)
	private Set<JobExperience> jobExperiences = new HashSet<>();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "curriculum", orphanRemoval = true)
	private Set<Language> languages = new HashSet<>();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "curriculum", orphanRemoval = true)
	private Set<Skill> skills = new HashSet<>();

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
		if ((obj == null) || (this.getClass() != obj.getClass())) {
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

	@Override
	public long getId() {
		return this.candidate.getId();
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

	public long getVersion() {
		return version;
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

	public void setId(final long id) {
		this.id = id;
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

	public void setVersion(final long version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Curriculum [education=" + this.education + ", jobExperiences=" + this.jobExperiences + ", languages="
				+ this.languages + ", skills=" + this.skills + "]";
	}
}
