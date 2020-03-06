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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.SortableField;
import org.hibernate.search.annotations.Store;

import ged.ejb.core.file.File;
import ged.ejb.core.model.Address;
import ged.ejb.core.model.Obfuscable;
import ged.ejb.core.model.Ownerable;
import ged.ejb.core.tag.Tag;
import ged.ejb.curriculum.Curriculum;
import ged.ejb.job.candidature.JobCandidature;
import ged.ejb.person.Person;
import ged.ejb.user.User;

@Entity
@Indexed
public class Candidate extends Person implements Ownerable, Obfuscable {

	private static final long serialVersionUID = 1L;

	@Embedded
	private Address address;

	private LocalDate bornDate;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "candidate")
	private Curriculum curriculum;

	@Min(0)
	private Integer expectedSalary;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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

	@ManyToOne
	@JoinColumn(name = "candidateId")
	private Origin origin;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "ownerId", nullable = false)
	@IndexedEmbedded
	private User owner;

	@Field(analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField
	private Integer rating;

	private Integer salary;

	private String skype;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "candidate_tag", joinColumns = @JoinColumn(name = "candidate_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
	@IndexedEmbedded
	private Set<Tag> tags = new HashSet<>();

	public void addFile(final File file) {
		this.files.add(file);
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

	public Integer getExpectedSalary() {
		return this.expectedSalary;
	}

	public Set<File> getFiles() {
		return this.files;
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

	public Origin getOrigin() {
		return this.origin;
	}

	@Override
	public User getOwner() {
		return this.owner;
	}

	public Integer getRating() {
		return this.rating;
	}

	public Integer getSalary() {
		return this.salary;
	}

	public void removeFile(File file) {
		Objects.requireNonNull(file);
		if (files == null) {
			throw new IllegalStateException();
		}
		this.files.remove(file);
	}

	public String getSkype() {
		return this.skype;
	}

	public Set<Tag> getTags() {
		return this.tags;
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

	public void setJobProfile(final String jobProfile) {
		this.jobProfile = jobProfile;
	}

	public void setLinkedinProfileUrl(final String linkedinProfileUrl) {
		this.linkedinProfileUrl = linkedinProfileUrl;
	}

	public void setOrigin(final Origin origin) {
		this.origin = origin;
	}

	@Override
	public void setOwner(final User owner) {
		this.owner = owner;
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
}