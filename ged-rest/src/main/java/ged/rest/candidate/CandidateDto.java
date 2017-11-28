package ged.rest.candidate;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.UploadedServerFile;
import ged.ejb.core.tag.Tag;
import ged.ejb.job.offer.JobCandidature;
import ged.rest.AbstractDto;

public class CandidateDto extends AbstractDto {

	@Temporal(TemporalType.DATE)
	private Date bornDate;

	private String city;

	@Pattern(regexp = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
	@NotNull
	private String email;

	@Min(0)
	private Integer expectedSalary;

	private Set<UploadedServerFile> files;

	private long id;

	private String imageFileName;

	private String infojobsProfileUrl;

	private List<JobCandidature> jobCandidatures;

	@NotNull
	private String jobProfile;

	private String linkedinProfileUrl;

	@NotNull
	private String name;

	private String ownerEmail;

	@NotNull
	@Pattern(regexp = "(?:[+]?(?:[0-9]{1,5}|\\x28[0-9]{1,5}\\x29)[ ]?)?[0-9]{2}(?:[0-9][ ]?){6}[0-9]")
	private String phoneNumber;

	private Integer rating;

	private Integer salary;

	@Column(length = 128)
	private String skype;

	private String state;

	@NotNull
	private String surename;

	private Set<Tag> tags;

	public CandidateDto() {
	}

	public CandidateDto(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		Objects.requireNonNull(candidate.getAddress());
		Objects.requireNonNull(candidate.getOwner());
		this.bornDate = candidate.getBornDate();
		this.city = candidate.getAddress().getCity();
		this.email = candidate.getEmail();
		this.expectedSalary = candidate.getExpectedSalary();
		this.files = candidate.getFiles();
		this.id = candidate.getId();
		this.imageFileName = candidate.getImageFileName();
		this.infojobsProfileUrl = candidate.getInfojobsProfileUrl();
		this.jobProfile = candidate.getJobProfile();
		this.linkedinProfileUrl = candidate.getLinkedinProfileUrl();
		this.name = candidate.getName();
		this.ownerEmail = candidate.getOwner().getEmail();
		this.phoneNumber = candidate.getPhoneNumber();
		this.rating = candidate.getRating();
		this.salary = candidate.getSalary();
		this.skype = candidate.getSkype();
		this.state = candidate.getAddress().getState();
		this.surename = candidate.getSurename();
		this.tags = candidate.getTags();
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
		final CandidateDto other = (CandidateDto) obj;
		return Objects.equals(this.email, other.email) && Objects.equals(this.name, other.name)
				&& Objects.equals(this.phoneNumber, other.phoneNumber) && Objects.equals(this.surename, other.surename);
	}

	public Date getBornDate() {
		return this.bornDate;
	}

	public String getCity() {
		return this.city;
	}

	public String getEmail() {
		return this.email;
	}

	public Integer getExpectedSalary() {
		return this.expectedSalary;
	}

	public Set<UploadedServerFile> getFiles() {
		return this.files;
	}

	public long getId() {
		return this.id;
	}

	public String getImageFileName() {
		return this.imageFileName;
	}

	public String getInfojobsProfileUrl() {
		return this.infojobsProfileUrl;
	}

	public List<JobCandidature> getJobCandidatures() {
		return this.jobCandidatures;
	}

	public String getJobProfile() {
		return this.jobProfile;
	}

	public String getLinkedinProfileUrl() {
		return this.linkedinProfileUrl;
	}

	public String getName() {
		return this.name;
	}

	public String getOwnerEmail() {
		return this.ownerEmail;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public Integer getRating() {
		return this.rating;
	}

	public Integer getSalary() {
		return this.salary;
	}

	public String getSkype() {
		return this.skype;
	}

	public String getState() {
		return this.state;
	}

	public String getSurename() {
		return this.surename;
	}

	public Set<Tag> getTags() {
		return this.tags;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.email, this.name, this.phoneNumber, this.surename);
	}

	public void setBornDate(final Date bornDate) {
		this.bornDate = bornDate;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setExpectedSalary(final Integer expectedSalary) {
		this.expectedSalary = expectedSalary;
	}

	public void setFiles(final Set<UploadedServerFile> files) {
		this.files = files;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public void setImageFileName(final String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public void setInfojobsProfileUrl(final String infojobsProfileUrl) {
		this.infojobsProfileUrl = infojobsProfileUrl;
	}

	public void setJobCandidatures(final List<JobCandidature> jobCandidatures) {
		this.jobCandidatures = jobCandidatures;
	}

	public void setJobProfile(final String jobProfile) {
		this.jobProfile = jobProfile;
	}

	public void setLinkedinProfileUrl(final String linkedinProfileUrl) {
		this.linkedinProfileUrl = linkedinProfileUrl;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setOwnerEmail(final String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setRating(final Integer rating) {
		this.rating = rating;
	}

	public void setSalary(final Integer salary) {
		this.salary = salary;
	}

	public void setSkype(final String skype) {
		this.skype = skype;
	}

	public void setState(final String state) {
		this.state = state;
	}

	public void setSurename(final String surename) {
		this.surename = surename;
	}

	public void setTags(final Set<Tag> tags) {
		this.tags = tags;
	}
}