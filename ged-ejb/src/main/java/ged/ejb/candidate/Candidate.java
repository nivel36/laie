package ged.ejb.candidate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import ged.ejb.core.Address;
import ged.ejb.core.file.ServerFile;
import ged.ejb.core.model.AbstractAuditedEntity;
import ged.ejb.core.tag.Tag;
import ged.ejb.curriculum.Curriculum;
import ged.ejb.job.offer.JobCandidature;

@Entity
@Indexed
public class Candidate extends AbstractAuditedEntity {

	private static final long serialVersionUID = 1305321530927456159L;

	@Embedded
	private Address address;

	private LocalDate bornDate;

	@OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "curriculumId", nullable = true, unique = true)
	private Curriculum curriculum;

	@Pattern(regexp = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
	@Column(length = 64, unique = true, nullable = false)
	private String email;

	@Min(0)
	private Integer expectedSalary;

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "candidate", orphanRemoval = true)
	private Set<ServerFile> files;

	private String imageFileName;

	private String infojobsProfileUrl;

	@OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<JobCandidature> jobCandidatures;

	@NotNull
	@Column(length = 64, nullable = false)
	@Field
	private String jobProfile;

	private String linkedinProfileUrl;

	@NotNull
	@Column(length = 32, nullable = false)
	@Field
	private String name;

	private String origin;

	@Pattern(regexp = "(?:[+]?(?:[0-9]{1,5}|\\x28[0-9]{1,5}\\x29)[ ]?)?[0-9]{2}(?:[0-9][ ]?){6}[0-9]")
	@Column(length = 12, nullable = false)
	private String phoneNumber;

	private Integer rating;

	private Integer salary;

	@Column(length = 128)
	private String skype;

	@NotNull
	@Column(length = 64, nullable = false)
	@Field
	private String surname;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "candidate_tag", joinColumns = @JoinColumn(name = "candidate_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
	@IndexedEmbedded
	private Set<Tag> tags = new HashSet<>();

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Candidate other = (Candidate) obj;
		return Objects.equals(this.email, other.email) && Objects.equals(this.name, other.name) && Objects.equals(this.phoneNumber, other.phoneNumber)
				&& Objects.equals(this.surname, other.surname);
	}

	public Address getAddress() {
		return this.address;
	}

	public LocalDate getBornDate() {
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

	public Set<ServerFile> getFiles() {
		return this.files;
	}

	public String getFullName() {
		return this.name + " " + this.surname;
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

	public String getOrigin() {
		return this.origin;
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

	public String getSurname() {
		return this.surname;
	}

	public Set<Tag> getTags() {
		return this.tags;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.email, this.name, this.phoneNumber, this.surname);
	}

	public void setAddress(final Address address) {
		this.address = address;
	}

	public void setBornDate(final LocalDate bornDate) {
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

	public void setFiles(final Set<ServerFile> files) {
		this.files = files;
	}

	public void setImageFileName(final String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public void setInfojobsProfileUrl(final String infojobsProfileUrl) {
		this.infojobsProfileUrl = infojobsProfileUrl;
	}

	public void setJobCandidature(final List<JobCandidature> jobCandidatures) {
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

	public void setOrigin(final String origin) {
		this.origin = origin;
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

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public void setTags(final Set<Tag> tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return this.getFullName();
	}
}