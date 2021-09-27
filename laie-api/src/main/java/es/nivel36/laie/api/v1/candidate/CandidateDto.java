package es.nivel36.laie.api.v1.candidate;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import es.nivel36.laie.api.v1.Dto;

public class CandidateDto implements Dto {

	@Temporal(TemporalType.DATE)
	private LocalDate bornDate;

	private String city;

	@Email
	@NotNull
	private String email;

	@Min(0)
	private Integer expectedSalary;

	private long id;

	private String imageFileName;

	private String infojobsProfileUrl;

	@NotNull
	private String jobProfile;

	private String linkedinProfileUrl;

	@NotNull
	private String name;

	private String ownerEmail;

	@NotNull
	private String phoneNumber;

	private Integer rating;

	private Integer salary;

	@Column(length = 128)
	private String skype;

	private String state;

	@NotNull
	private String surname;

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
		return Objects.equals(this.email, other.email) && Objects.equals(this.name, other.name) && Objects.equals(this.phoneNumber, other.phoneNumber)
				&& Objects.equals(this.surname, other.surname);
	}

	public LocalDate getBornDate() {
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

	public long getId() {
		return this.id;
	}

	public String getImageFileName() {
		return this.imageFileName;
	}

	public String getInfojobsProfileUrl() {
		return this.infojobsProfileUrl;
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

	public String getSurname() {
		return this.surname;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.email, this.name, this.phoneNumber, this.surname);
	}

	public void setBornDate(final LocalDate bornDate) {
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

	public void setId(final long id) {
		this.id = id;
	}

	public void setImageFileName(final String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public void setInfojobsProfileUrl(final String infojobsProfileUrl) {
		this.infojobsProfileUrl = infojobsProfileUrl;
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

	public void setSurname(final String surname) {
		this.surname = surname;
	}
}