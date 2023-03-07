package es.nivel36.laie.ejb.candidate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;

import es.nivel36.laie.ejb.core.action.Auditable;
import es.nivel36.laie.ejb.core.file.PhysicalFile;
import es.nivel36.laie.ejb.core.model.AbstractEntity;
import es.nivel36.laie.ejb.core.model.Address;
import es.nivel36.laie.ejb.core.model.Ownerable;
import es.nivel36.laie.ejb.core.subject.Subject;
import es.nivel36.laie.ejb.core.tag.Tag;
import es.nivel36.laie.ejb.curriculum.Curriculum;
import es.nivel36.laie.ejb.job.candidature.JobCandidature;
import es.nivel36.laie.ejb.job.meeting.Meeting;
import es.nivel36.laie.ejb.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Indexed
@Table(name = "CANDIDATE", indexes = { @Index(name = "UX_CANDIDATE_EMAIL", columnList = "EMAIL", unique = true) }, //
		uniqueConstraints = { @UniqueConstraint(name = "UQ_CANDIDATE_EMAIL", columnNames = { "EMAIL" }) })
public class Candidate extends AbstractEntity implements Ownerable, Subject, Auditable {

	private static final long serialVersionUID = -7470903145789563432L;

	@Embedded
	private Address address;

	@Column(name = "BORN_DATE")
	private LocalDate bornDate;

	@OneToOne(mappedBy = "candidate")
	private Curriculum curriculum;

	@Email
	@NotNull
	@Column(name = "EMAIL")
	@FullTextField(name = "_email")
	private String email;

	@Min(0)
	@Column(name = "EXPECTED_SALARY", scale = 0, precision = 6)
	private Integer expectedSalary;

	@OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<File> files = new HashSet<>();

	@Column(name = "INFOJOBS_PROFILE_URL", columnDefinition = "TEXT")
	private String infojobsProfileUrl;

	@OneToMany(mappedBy = "candidate", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private Set<JobCandidature> jobCandidatures = new HashSet<>();

	@NotNull
	@FullTextField(name = "_jobProfile")
	@Column(name = "JOB_PROFILE", nullable = false, columnDefinition = "TEXT")
	private String jobProfile;

	@Column(name = "LINKEDIN_PROFILE_URL", columnDefinition = "TEXT")
	private String linkedinProfileUrl;

	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Meeting> meetings = new HashSet<>();

	@NotNull
	@Column(name = "NAME", nullable = false, columnDefinition = "TEXT")
	@FullTextField(name = "_name")
	@GenericField(sortable = Sortable.YES)
	private String name;

	@ManyToOne
	@JoinColumn(name = "CANDIDATE_ID")
	private Origin origin;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OWNER_ID", nullable = false)
	private User owner;

	@Column(name = "PHONE_NUMBER", columnDefinition = "TEXT")
	private String phoneNumber;

	@ManyToOne
	@JoinColumn(name = "PICTURE_ID")
	private PhysicalFile picture;

	@GenericField(sortable = Sortable.YES)
	@Column(name = "RATING", scale = 0, precision = 1)
	private Integer rating;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "candidate", orphanRemoval = true)
	private Set<Rating> ratings = new HashSet<>();

	@Column(name = "SALARY", scale = 0, precision = 6)
	private Integer salary;

	@Column(name = "SKYPE", columnDefinition = "TEXT")
	private String skype;

	@NotNull
	@Column(name = "SURNAME", columnDefinition = "TEXT")
	@FullTextField(name = "_surname")
	private String surname;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "CANDIDATE_TAG", joinColumns = @JoinColumn(name = "CANDIDATE_ID"), inverseJoinColumns = @JoinColumn(name = "TAG_ID"))
	@IndexedEmbedded(includeDepth = 1)
	private Set<Tag> tags = new HashSet<>();

	public void addFile(final File file) {
		Objects.requireNonNull(file);
		file.setCandidate(this);
		this.files.add(file);
	}

	public void addMeeting(final Meeting meeting) {
		Objects.requireNonNull(meeting);
		this.meetings.add(meeting);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Candidate other = (Candidate) obj;
		return Objects.equals(other.email, this.email);
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

	@Override
	public String getEntityName() {
		return "CANDIDATE";
	}

	@Override
	public String getEntityTitle() {
		return this.getFullName();
	}

	public Integer getExpectedSalary() {
		return this.expectedSalary;
	}

	public Set<File> getFiles() {
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

	public Set<JobCandidature> getJobCandidatures() {
		return this.jobCandidatures;
	}

	public String getJobProfile() {
		return this.jobProfile;
	}

	public String getLinkedinProfileUrl() {
		return this.linkedinProfileUrl;
	}

	public Set<Meeting> getMeetings() {
		return meetings;
	}

	public String getName() {
		return this.name;
	}

	public Origin getOrigin() {
		return this.origin;
	}

	@Override
	public User getOwner() {
		return this.owner;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public PhysicalFile getPicture() {
		return this.picture;
	}

	public Integer getRating() {
		return this.rating;
	}

	public Set<Rating> getRatings() {
		return ratings;
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
		return 31 * Objects.hash(this.email);
	}

	public void removeFile(final File file) {
		Objects.requireNonNull(file);
		this.files.remove(file);
		file.setCandidate(null);
	}

	public void removeMeeting(final Meeting meeting) {
		Objects.requireNonNull(meeting);
		this.meetings.remove(meeting);
	}

	public void setAddress(final Address address) {
		this.address = address;
	}

	public void setBornDate(final LocalDate bornDate) {
		this.bornDate = bornDate;
	}

	public void setCurriculum(final Curriculum curriculum) {
		this.curriculum = curriculum;
		curriculum.setCandidate(this);
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setExpectedSalary(final Integer expectedSalary) {
		this.expectedSalary = expectedSalary;
	}

	public void setFiles(final Set<File> files) {
		this.files = files;
	}

	public void setInfojobsProfileUrl(final String infojobsProfileUrl) {
		this.infojobsProfileUrl = infojobsProfileUrl;
	}

	public void setJobCandidature(final Set<JobCandidature> jobCandidatures) {
		this.jobCandidatures = jobCandidatures;
	}

	public void setJobCandidatures(Set<JobCandidature> jobCandidatures) {
		this.jobCandidatures = jobCandidatures;
	}

	public void setJobProfile(final String jobProfile) {
		this.jobProfile = jobProfile;
	}

	public void setLinkedinProfileUrl(final String linkedinProfileUrl) {
		this.linkedinProfileUrl = linkedinProfileUrl;
	}

	public void setMeetings(Set<Meeting> meetings) {
		this.meetings = meetings;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setOrigin(final Origin origin) {
		this.origin = origin;
	}

	@Override
	public void setOwner(final User owner) {
		this.owner = owner;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setPicture(final PhysicalFile picture) {
		this.picture = picture;
	}

	public void setRating(final Integer rating) {
		this.rating = rating;
	}

	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
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

	public void setTags(final List<Tag> tags) {
		if (tags == null) {
			this.tags = new HashSet<>();
		} else {
			this.tags = new HashSet<>(tags);
		}
	}

	public void setTags(final Set<Tag> tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return this.getFullName();
	}
}