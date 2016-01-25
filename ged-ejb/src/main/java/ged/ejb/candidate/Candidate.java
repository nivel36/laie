package ged.ejb.candidate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import ged.ejb.core.AbstractEntity;
import ged.ejb.curriculum.Curriculum;
import ged.ejb.job.JobCandidature;
import ged.ejb.tag.Tag;

@Entity
@NamedQueries({ @NamedQuery(name = "Candidate.getById", query = "SELECT c FROM Candidate c LEFT JOIN FETCH c.curriculum LEFT JOIN FETCH c.curriculum.education LEFT JOIN FETCH c.curriculum.jobExperiences LEFT JOIN FETCH c.curriculum.languages LEFT JOIN FETCH c.curriculum.skills   WHERE c.id = :id") })
public class Candidate extends AbstractEntity {

	private static final long serialVersionUID = 1305321530927456159L;

	@Embedded
	private Address address;

	@Temporal(TemporalType.DATE)
	private Date bornDate;

	@OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "curriculumId", nullable = true, unique = true)
	private Curriculum curriculum;

	@Pattern(regexp = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
	@NotNull
	@Column(length = 64, unique = true, nullable = false)
	private String email;

	@NotNull
	@Column(length = 32, nullable = false)
	private String firstSurename;

	@OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<JobCandidature> jobCandidature;

	@NotNull
	@Column(length = 32, nullable = false)
	private String name;

	@NotNull
	@Pattern(regexp = "(?:[+]?(?:[0-9]{1,5}|\\x28[0-9]{1,5}\\x29)[ ]?)?[0-9]{2}(?:[0-9][ ]?){6}[0-9]")
	@Column(length = 12, nullable = false)
	private String phoneNumber1;

	// TODO: Añadir el pattern cunado se arregle el envío de null
	@Column(length = 12)
	private String phoneNumber2;

	@Column(length = 32)
	private String secondSurename;

	@OneToMany
	private List<Tag> tags = new ArrayList<Tag>();

	public Candidate() {
		this.address = new Address();
	}

	public Address getAddress() {
		return address;
	}

	public Date getBornDate() {
		return bornDate;
	}

	public Curriculum getCurriculum() {
		return curriculum;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstSurename() {
		return firstSurename;
	}

	public List<JobCandidature> getJobCandidature() {
		return jobCandidature;
	}

	public String getName() {
		return name;
	}

	public String getPhoneNumber1() {
		return phoneNumber1;
	}

	public String getPhoneNumber2() {
		return phoneNumber2;
	}

	public String getSecondSurename() {
		return secondSurename;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public void setBornDate(Date bornDate) {
		this.bornDate = bornDate;
	}

	public void setCurriculum(Curriculum curriculum) {
		this.curriculum = curriculum;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstSurename(String firstSurename) {
		this.firstSurename = firstSurename;
	}

	public void setJobCandidature(List<JobCandidature> jobCandidature) {
		this.jobCandidature = jobCandidature;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhoneNumber1(String phoneNumber1) {
		this.phoneNumber1 = phoneNumber1;
	}

	public void setPhoneNumber2(String phoneNumber2) {
		this.phoneNumber2 = phoneNumber2;
	}

	public void setSecondSurename(String secondSurename) {
		this.secondSurename = secondSurename;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Candidate other = (Candidate) obj;
		return id == other.id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) id;
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(name);
		sb.append(" ").append(firstSurename);
		if (secondSurename != null || !secondSurename.trim().equals("")) {
			sb.append(" ").append(secondSurename);
		}
		return sb.toString();
	}
}
