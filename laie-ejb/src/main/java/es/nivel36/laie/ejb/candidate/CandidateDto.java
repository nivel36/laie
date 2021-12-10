package es.nivel36.laie.ejb.candidate;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import es.nivel36.laie.ejb.core.file.FileDto;
import es.nivel36.laie.ejb.core.model.AddressDto;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureDto;

public class CandidateDto implements Serializable {

	private static final long serialVersionUID = 3735581019757384532L;

	private AddressDto address;

	private LocalDate bornDate;

	protected String email;

	private Integer expectedSalary;

	private Set<FileDto> files = new HashSet<>();

	private String infojobsProfileUrl;

	private List<JobCandidatureDto> jobCandidatures;

	private String jobProfile;

	private String linkedinProfileUrl;

	protected String name;

	private Origin origin;

	protected String phoneNumber;

	protected String avatarUrl;

	private Integer rating;

	private Integer salary;

	private String skype;

	protected String surname;

	private Set<String> tags = new HashSet<>();

	private String uid;

	public AddressDto getAddress() {
		if(this.address == null) {
			return new AddressDto();
		}
		return this.address;
	}

	public LocalDate getBornDate() {
		return this.bornDate;
	}

	public String getEmail() {
		return this.email;
	}

	public Integer getExpectedSalary() {
		return this.expectedSalary;
	}

	public Set<FileDto> getFiles() {
		return this.files;
	}

	public String getFullName() {
		if (this.name == null) {
			return null;
		}
		return new StringBuilder(this.name).append(" ").append(this.surname).toString();
	}

	public String getInfojobsProfileUrl() {
		return this.infojobsProfileUrl;
	}

	public List<JobCandidatureDto> getJobCandidatures() {
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

	public Origin getOrigin() {
		return this.origin;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public String getAvatarUrl() {
		return this.avatarUrl;
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

	public String getSurname() {
		return this.surname;
	}

	public Set<String> getTags() {
		return this.tags;
	}

	public String getUid() {
		return this.uid;
	}

	public void setAddress(final AddressDto address) {
		this.address = address;
	}

	public void setBornDate(final LocalDate bornDate) {
		this.bornDate = bornDate;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setExpectedSalary(final Integer expectedSalary) {
		this.expectedSalary = expectedSalary;
	}

	public void setFiles(final Set<FileDto> files) {
		this.files = files;
	}

	public void setInfojobsProfileUrl(final String infojobsProfileUrl) {
		this.infojobsProfileUrl = infojobsProfileUrl;
	}

	public void setJobCandidature(final List<JobCandidatureDto> jobCandidatures) {
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

	public void setOrigin(final Origin origin) {
		this.origin = origin;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setAvatarUrl(final String avatarUrl) {
		this.avatarUrl = avatarUrl;
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

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public void setTags(final List<String> tags) {
		if (tags == null) {
			this.tags = new HashSet<>();
		} else {
			this.tags = new HashSet<>(tags);
		}
	}

	public void setTags(final Set<String> tags) {
		this.tags = tags;
	}

	void setUid(final String uid) {
		this.uid = uid;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj) || (this.getClass() != obj.getClass())) {
			return false;
		}
		final Candidate other = (Candidate) obj;
		return Objects.equals(other.email, this.email);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.email);
	}

	@Override
	public String toString() {
		return this.getFullName();
	}
}
