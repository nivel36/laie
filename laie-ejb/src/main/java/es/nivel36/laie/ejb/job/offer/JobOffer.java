package es.nivel36.laie.ejb.job.offer;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.core.action.Auditable;
import es.nivel36.laie.ejb.core.model.AbstractEntity;
import es.nivel36.laie.ejb.core.model.Address;
import es.nivel36.laie.ejb.core.model.Ownerable;
import es.nivel36.laie.ejb.job.submission.JobSubmission;
import es.nivel36.laie.ejb.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Indexed
@Table(name = "JOB_OFFER",  uniqueConstraints = {})
public class JobOffer extends AbstractEntity implements Ownerable, Auditable {

	private static final long serialVersionUID = 1529439068651089035L;

	@Embedded
	@IndexedEmbedded(includeDepth = 1)
	private Address address;

	@ManyToOne
	@JoinColumn(name = "CLIENT_ID", nullable = false)
	@NotNull
	@IndexedEmbedded(includeDepth = 1)
	private Client client;

	@GenericField(sortable = Sortable.YES)
	@Column(name = "CLOSE_DATE")
	private LocalDate closeDate;

	@FullTextField
	@Column(name = "DESCRIPTION", columnDefinition = "TEXT")
	private String description;

	@OneToMany(mappedBy = "jobOffer", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<JobSubmission> jobSubmissions = new HashSet<>();

	@OneToMany(mappedBy = "jobOffer", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<JobOfferEvent> jobOfferEvents = new HashSet<>();

	@NotNull
	@ManyToOne
	@JoinColumn(name = "JOB_OFFER_PROCESS_ID", nullable = false)
	private JobOfferProcess jobOfferProcess;

	@Column(name = "MAX_SALARY")
	private Integer maxSalary;

	@Column(name = "MIN_SALARY")
	private Integer minSalary;

	@NotNull
	@GenericField(sortable = Sortable.YES)
	@Column(name = "OPEN_DATE", nullable = false)
	private LocalDate openDate;

	@NotNull
	@GenericField(sortable = Sortable.YES)
	@Column(name = "CREATION_DATE", nullable = false)
	private LocalDate creationDate;

	@GenericField(sortable = Sortable.YES)
	@Column(name = "COMPLETION_DATE")
	private LocalDate completionDate;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OWNER_ID", nullable = false)
	@IndexedEmbedded(includeDepth = 1)
	private User owner;

	@NotNull
	@Column(name = "POSITIONS", nullable = false)
	private int positions = 1;

	@NotNull
	@Column(name = "PUBLISHED", nullable = false)
	private boolean published;

	@ManyToMany
	@JoinTable(name = "JOB_PERSON", joinColumns = @JoinColumn(name = "JOB_ID"), inverseJoinColumns = @JoinColumn(name = "PERSON_ID"))
	private Set<User> recruiters = new HashSet<>();

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "STATE", nullable = false)
	private JobOfferState state;

	@NotBlank
	@Column(name = "TITLE", nullable = false)
	@FullTextField(name = "_title")
	@KeywordField(sortable = Sortable.YES)
	private String title;

	public Address getAddress() {
		return this.address;
	}

	public Client getClient() {
		return this.client;
	}

	public LocalDate getCloseDate() {
		return this.closeDate;
	}

	public String getDescription() {
		return this.description;
	}

	@Override
	public String getEntityName() {
		return "JOB_OFFER";
	}

	@Override
	public String getEntityTitle() {
		return this.title;
	}

	public Set<JobSubmission> getJobSubmissions() {
		return this.jobSubmissions;
	}

	public Set<JobOfferEvent> getJobOfferEvents() {
		return jobOfferEvents;
	}

	public JobOfferProcess getJobOfferProcess() {
		return jobOfferProcess;
	}

	public Integer getMaxSalary() {
		return this.maxSalary;
	}

	public Integer getMinSalary() {
		return this.minSalary;
	}

	public LocalDate getOpenDate() {
		return this.openDate;
	}

	public LocalDate getCreationDate() {
		return this.creationDate;
	}

	public LocalDate getCompletionDate() {
		return this.completionDate;
	}

	@Override
	public User getOwner() {
		return this.owner;
	}

	public Integer getPositions() {
		return this.positions;
	}

	public Set<User> getRecruiters() {
		return this.recruiters;
	}

	public JobOfferState getState() {
		return this.state;
	}

	public String getTitle() {
		return this.title;
	}

	public boolean hasSubmissionOf(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		if (this.jobSubmissions.isEmpty()) {
			return false;
		}
		for (final JobSubmission jobSubmission : this.jobSubmissions) {
			if (jobSubmission.getCandidate().equals(candidate)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasState(final JobOfferState state) {
		if (this.state == null) {
			return state == null;
		}
		return this.state.equals(state);
	}

	public boolean isOpen() {
		return this.hasState(JobOfferState.OPENED);
	}

	public boolean isPublished() {
		return this.published;
	}

	public void setAddress(final Address address) {
		this.address = address;
	}

	public void setClient(final Client client) {
		this.client = client;
	}

	public void setCloseDate(final LocalDate closeDate) {
		this.closeDate = closeDate;
	}

	public void setCreationDate(final LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public void setCompletionDate(final LocalDate completionDate) {
		this.completionDate = completionDate;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setJobSubmissions(final Set<JobSubmission> jobSubmissions) {
		this.jobSubmissions = jobSubmissions;
	}

	public void setJobOfferEvents(final Set<JobOfferEvent> jobOfferEvents) {
		this.jobOfferEvents = jobOfferEvents;
	}

	public void setJobOfferProcess(final JobOfferProcess jobOfferProcess) {
		this.jobOfferProcess = jobOfferProcess;
	}

	public void setMaxSalary(final Integer maxSalary) {
		this.maxSalary = maxSalary;
	}

	public void setMinSalary(final Integer minSalary) {
		this.minSalary = minSalary;
	}

	public void setOpenDate(final LocalDate openDate) {
		this.openDate = openDate;
	}

	@Override
	public void setOwner(final User owner) {
		this.owner = owner;
	}

	public void setPositions(final Integer positions) {
		this.positions = positions;
	}

	public void setPublished(final boolean published) {
		this.published = published;
	}

	public void setRecruiters(final Set<User> recruiters) {
		this.recruiters = recruiters;
	}

	public void setState(final JobOfferState state) {
		this.state = state;
	}

	public void setTitle(final String title) {
		this.title = title;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (this.getClass() != obj.getClass())) {
			return false;
		}
		final JobOffer other = (JobOffer) obj;
		return Objects.equals(this.openDate, other.openDate) && Objects.equals(this.title, other.title)
				&& Objects.equals(this.positions, other.positions);
	}
	
	@Override
	public int hashCode() {
		return 31 * Objects.hash(this.openDate, this.title, this.positions);
	}

	@Override
	public String toString() {
		return this.title + "-" + this.client.getName();
	}
}
