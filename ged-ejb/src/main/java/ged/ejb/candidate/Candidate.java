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

import ged.ejb.core.model.AbstractAuditedEntity;
import ged.ejb.core.tag.Tag;
import ged.ejb.curriculum.Curriculum;
import ged.ejb.curriculum.FileSys;
import ged.ejb.job.JobCandidature;
import ged.ejb.user.User;

@Entity
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

	@OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<JobCandidature> jobCandidature;

	@NotNull
	@Column(length = 32, nullable = false)
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

	private String position;

	private Integer salary;

	@NotNull
	@Column(length = 64, nullable = false)
	private String surename;

	@OneToMany
	private List<Tag> tags = new ArrayList<Tag>();

	public Candidate() {
		this.address = new Address();
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

	public List<JobCandidature> getJobCandidature() {
		return this.jobCandidature;
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

	public void setJobCandidature(final List<JobCandidature> jobCandidature) {
		this.jobCandidature = jobCandidature;
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
