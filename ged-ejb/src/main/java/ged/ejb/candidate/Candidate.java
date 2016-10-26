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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import ged.ejb.core.Address;
import ged.ejb.core.model.AbstractAuditedEntity;
import ged.ejb.core.tag.Tag;
import ged.ejb.curriculum.Curriculum;
import ged.ejb.curriculum.FileSys;
import ged.ejb.job.JobCandidature;
import ged.ejb.user.User;

@Entity
@Indexed
public class Candidate extends AbstractAuditedEntity {

	private static final long serialVersionUID = 1305321530927456159L;

	@Embedded
	private Address address;

	private Integer age;

	@Temporal(TemporalType.DATE)
	private Date bornDate;

	@OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "curriculumId", nullable = true, unique = true)
	private Curriculum curriculum;

	@Pattern(regexp = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
	@NotNull
	@Column(length = 64, unique = true, nullable = false)
	private String email;

	private Integer expectedSalary;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "candidate", orphanRemoval = true)
	private List<FileSys> files;

	private String infojobsProfileUrl;

	@OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<JobCandidature> jobCandidatures;

	private String linkedinProfileUrl;

	@NotNull
	@Column(length = 32, nullable = false)
	@Field
	private String name;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "ownerId", nullable = false)
	private User owner;

	@NotNull
	@Pattern(regexp = "(?:[+]?(?:[0-9]{1,5}|\\x28[0-9]{1,5}\\x29)[ ]?)?[0-9]{2}(?:[0-9][ ]?){6}[0-9]")
	@Column(length = 12, nullable = false)
	private String phoneNumber1;

	@Pattern(regexp = "(?:[+]?(?:[0-9]{1,5}|\\x28[0-9]{1,5}\\x29)[ ]?)?[0-9]{2}(?:[0-9][ ]?){6}[0-9]")
	@Column(length = 12)
	private String phoneNumber2;

	@NotNull
	@Column(length = 64, nullable = false)
	@Field
	private String position;

	private Integer salary;

	@NotNull
	@Column(length = 64, nullable = false)
	@Field
	private String surename;

	@OneToMany
	private List<Tag> tags;

	public void addJobCandidature(final JobCandidature jobCandidature) {
		if (jobCandidature == null) {
			throw new NullPointerException();
		}
		if (this.jobCandidatures == null) {
			this.jobCandidatures = new ArrayList<>();
		}
		jobCandidature.setCandidate(this);
		this.jobCandidatures.add(jobCandidature);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Candidate other = (Candidate) obj;
		if (this.email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!this.email.equals(other.email)) {
			return false;
		}
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		if (this.phoneNumber1 == null) {
			if (other.phoneNumber1 != null) {
				return false;
			}
		} else if (!this.phoneNumber1.equals(other.phoneNumber1)) {
			return false;
		}
		if (this.surename == null) {
			if (other.surename != null) {
				return false;
			}
		} else if (!this.surename.equals(other.surename)) {
			return false;
		}
		return true;
	}

	public Address getAddress() {
		return this.address;
	}

	public Integer getAge() {
		return this.age;
	}

	public Date getBornDate() {
		return this.bornDate;
	}

	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	public String getEmail() {
		return this.email;
	}

	public Integer getExpectedSalary() {
		return this.expectedSalary;
	}

	public List<FileSys> getFiles() {
		return this.files;
	}

	public String getInfojobsProfileUrl() {
		return this.infojobsProfileUrl;
	}

	public List<JobCandidature> getJobCandidatures() {
		return this.jobCandidatures;
	}

	public String getLinkedinProfileUrl() {
		return this.linkedinProfileUrl;
	}

	public String getName() {
		return this.name;
	}

	public User getOwner() {
		return this.owner;
	}

	public String getPhoneNumber1() {
		return this.phoneNumber1;
	}

	public String getPhoneNumber2() {
		return this.phoneNumber2;
	}

	public String getPosition() {
		return this.position;
	}

	public Integer getSalary() {
		return this.salary;
	}

	public String getSurename() {
		return this.surename;
	}

	public List<Tag> getTags() {
		return this.tags;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = (prime * result) + ((this.email == null) ? 0 : this.email.hashCode());
		result = (prime * result) + ((this.name == null) ? 0 : this.name.hashCode());
		result = (prime * result) + ((this.phoneNumber1 == null) ? 0 : this.phoneNumber1.hashCode());
		result = (prime * result) + ((this.surename == null) ? 0 : this.surename.hashCode());
		return result;
	}

	public void setAddress(final Address address) {
		this.address = address;
	}

	public void setAge(final Integer age) {
		this.age = age;
	}

	public void setBornDate(final Date bornDate) {
		this.bornDate = bornDate;
	}

	public void setCurriculum(final Curriculum curriculum) {
		this.curriculum = curriculum;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setExpectedSalary(final Integer expectedSalary) {
		this.expectedSalary = expectedSalary;
	}

	public void setFiles(final List<FileSys> files) {
		this.files = files;
	}

	public void setInfojobsProfileUrl(final String infojobsProfileUrl) {
		this.infojobsProfileUrl = infojobsProfileUrl;
	}

	public void setJobCandidature(final List<JobCandidature> jobCandidatures) {
		this.jobCandidatures = jobCandidatures;
	}

	public void setLinkedinProfileUrl(final String linkedinProfileUrl) {
		this.linkedinProfileUrl = linkedinProfileUrl;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setOwner(final User owner) {
		this.owner = owner;
	}

	public void setPhoneNumber1(final String phoneNumber1) {
		this.phoneNumber1 = phoneNumber1;
	}

	public void setPhoneNumber2(final String phoneNumber2) {
		this.phoneNumber2 = phoneNumber2;
	}

	public void setPosition(final String position) {
		this.position = position;
	}

	public void setSalary(final Integer salary) {
		this.salary = salary;
	}

	public void setSurename(final String surename) {
		this.surename = surename;
	}

	public void setTags(final List<Tag> tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return this.name + " " + this.surename;
	}
}