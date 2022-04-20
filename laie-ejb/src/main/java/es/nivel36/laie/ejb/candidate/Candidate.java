package es.nivel36.laie.ejb.candidate;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.SortableField;
import org.hibernate.search.annotations.Store;

import es.nivel36.laie.ejb.core.file.File;
import es.nivel36.laie.ejb.core.model.AbstractEntity;
import es.nivel36.laie.ejb.core.model.Address;
import es.nivel36.laie.ejb.core.model.Ownerable;
import es.nivel36.laie.ejb.core.tag.Tag;
import es.nivel36.laie.ejb.curriculum.Curriculum;
import es.nivel36.laie.ejb.job.candidature.JobCandidature;
import es.nivel36.laie.ejb.user.User;

@Entity
@Indexed
@Table(indexes = { @javax.persistence.Index(name = "UX_CANDIDATE_EMAIL", columnList = "email", unique = true) })
public class Candidate extends AbstractEntity implements Ownerable {

	private static final long serialVersionUID = -7470903145789563432L;

	@Embedded
	private Address address;

	private LocalDate bornDate;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "candidate")
	private Curriculum curriculum;

	@Email
	@NotNull
	@Column(length = 128, nullable = false, unique = true)
	@Field
	protected String email;

	@Min(0)
	private Integer expectedSalary;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<File> files = new HashSet<>();

	private String infojobsProfileUrl;

	@OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<JobCandidature> jobCandidatures;

	@NotNull
	@Column(nullable = false)
	@Field(name = "_jobProfile")
	@Field(name = "jobProfile", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField(forField = "jobProfile")
	private String jobProfile;

	private String linkedinProfileUrl;

	@NotNull
	@Column(nullable = false)
	@Field(name = "_name")
	@Field(name = "name", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField(forField = "name")
	protected String name;

	@ManyToOne
	@JoinColumn(name = "candidateId")
	private Origin origin;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "ownerId", nullable = false)
	@IndexedEmbedded
	private User owner;

	@Column(length = 12)
	protected String phoneNumber;

	@ManyToOne
	@JoinColumn(name = "picture")
	protected File picture;

	@Field(analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField
	private Integer rating;

	private Integer salary;

	private String skype;

	@NotNull
	@Column(nullable = false)
	@Field(name = "_surname")
	@Field(name = "surname", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField(forField = "surname")
	protected String surname;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "candidate_tag", joinColumns = @JoinColumn(name = "candidate_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
	@IndexedEmbedded
	private Set<Tag> tags = new HashSet<>();

	public void addFile(final File file) {
		this.files.add(file);
	}

	public Address getAddress() {
		if (this.address == null) {
			this.address = new Address();
		}
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

	public File getPicture() {
		return this.picture;
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
	
	public void removeFile(File file) {
		Objects.requireNonNull(file);
		if (files == null) {
			throw new IllegalStateException();
		}
		this.files.remove(file);
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

	public void setFiles(final Set<File> files) {
		this.files = files;
	}

	public void setInfojobsProfileUrl(final String infojobsProfileUrl) {
		this.infojobsProfileUrl = infojobsProfileUrl;
	}

	public void setJobCandidature(final List<JobCandidature> jobCandidatures) {
		this.jobCandidatures = jobCandidatures;
	}

	public void setJobCandidatures(List<JobCandidature> jobCandidatures) {
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

	@Override
	public void setOwner(final User owner) {
		this.owner = owner;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setPicture(final File picture) {
		this.picture = picture;
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
	public boolean equals(final Object obj) {
		if(obj == null) {
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
	
	@Override
	public int hashCode() {
		return 31 * Objects.hash(this.email);
	}

	@Override
	public String toString() {
		return this.getFullName();
	}
}