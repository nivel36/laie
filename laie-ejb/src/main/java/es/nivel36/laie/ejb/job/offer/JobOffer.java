package es.nivel36.laie.ejb.job.offer;

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
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.SortableField;
import org.hibernate.search.annotations.Store;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.core.model.AbstractObfuscableIndexableEntity;
import es.nivel36.laie.ejb.core.model.Address;
import es.nivel36.laie.ejb.core.model.Ownerable;
import es.nivel36.laie.ejb.job.candidature.JobCandidature;
import es.nivel36.laie.ejb.user.User;

@Entity
@Indexed
@Table(name = "JOB_OFFER")
public class JobOffer extends AbstractObfuscableIndexableEntity implements Ownerable {

	private static final long serialVersionUID = 1529439068651089035L;

	@Embedded
	@IndexedEmbedded
	private Address address;

	@ManyToOne
	@JoinColumn(name = "clientId", nullable = false)
	@NotNull
	@IndexedEmbedded
	private Client client;

	@Field(analyze = Analyze.NO)
	@SortableField
	private LocalDate closeDate;

	@NotNull
	@Field(analyze = Analyze.NO)
	@SortableField
	@Column(nullable = false)
	private LocalDate openDate;

	@Lob
	@Field
	private String description;

	@OneToMany(mappedBy = "jobOffer", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<JobCandidature> jobCandidatures = new HashSet<>();

	@NotNull
	@ManyToOne
	@JoinColumn(name = "ownerId", nullable = false)
	@IndexedEmbedded
	private User owner;

	@NotNull
	private Integer places = 1;

	private boolean published;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "job_user", joinColumns = @JoinColumn(name = "job_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> recruiters = new HashSet<>();;

	private Integer salary;

	@NotNull
	private JobOfferState state;

	@NotNull
	@Column(nullable = false)
	@Field(name = "_title")
	@Field(name = "title", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField(forField = "title")
	private String title;

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
		final JobOffer other = (JobOffer) obj;
		return Objects.equals(this.openDate, other.openDate) && Objects.equals(this.title, other.title)
				&& Objects.equals(this.places, other.places);
	}

	public Address getAddress() {
		if (this.address == null) {
			this.address = new Address();
		}
		return this.address;
	}

	public Client getClient() {
		return this.client;
	}

	public LocalDate getCloseDate() {
		return this.closeDate;
	}

	public LocalDate getOpenDate() {
		return this.openDate;
	}

	public String getDescription() {
		return this.description;
	}

	public Set<JobCandidature> getJobCandidatures() {
		return this.jobCandidatures;
	}

	@Override
	public User getOwner() {
		return this.owner;
	}

	public Integer getPlaces() {
		return this.places;
	}

	public Set<User> getRecruiters() {
		return this.recruiters;
	}

	public Integer getSalary() {
		return this.salary;
	}

	public JobOfferState getState() {
		return this.state;
	}

	public String getTitle() {
		return this.title;
	}

	public boolean hasCandidatureOf(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		if (this.jobCandidatures.isEmpty()) {
			return false;
		}
		for (final JobCandidature jobCandidature : this.jobCandidatures) {
			if (jobCandidature.getCandidate().equals(candidate)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.openDate, this.title, this.places);
	}

	public boolean hasState(final JobOfferState state) {
		if (this.state == null) {
			return state == null;
		} else {
			return this.state.equals(state);
		}
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

	public void setOpenDate(final LocalDate openDate) {
		this.openDate = openDate;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setJobCandidatures(final Set<JobCandidature> jobCandidatures) {
		this.jobCandidatures = jobCandidatures;
	}

	@Override
	public void setOwner(final User owner) {
		this.owner = owner;
	}

	public void setPlaces(final Integer places) {
		this.places = places;
	}

	public void setPublished(final boolean published) {
		this.published = published;
	}

	public void setRecruiters(final List<User> users) {
		if ((users == null) || users.isEmpty()) {
			return;
		}
		this.recruiters = new HashSet<>(users);
	}

	public void setRecruiters(final Set<User> recruiters) {
		this.recruiters = recruiters;
	}

	public void setSalary(final Integer salary) {
		this.salary = salary;
	}

	public void setState(final JobOfferState state) {
		this.state = state;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return this.title + "-" + this.client.getName();
	}
}
