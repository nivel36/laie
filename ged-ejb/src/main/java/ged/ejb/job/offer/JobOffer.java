package ged.ejb.job.offer;

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

import ged.ejb.client.Client;
import ged.ejb.core.model.AbstractEntity;
import ged.ejb.core.model.Address;
import ged.ejb.core.model.Ownerable;
import ged.ejb.job.candidature.JobCandidature;
import ged.ejb.user.User;

@Entity
@Indexed
@Table(name = "JOB_OFFER")
public class JobOffer extends AbstractEntity implements Ownerable {

	private static final long serialVersionUID = 1L;

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
	private LocalDate dateClosed;

	@Field(analyze = Analyze.NO)
	@SortableField
	private LocalDate dateOpened;

	@Lob
	@Field
	private String description;

	@OneToMany(mappedBy = "jobOffer", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<JobCandidature> jobCandidatures;

	@NotNull
	private JobOfferState jobOfferState;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "ownerId", nullable = false)
	@IndexedEmbedded
	private User owner;

	@NotNull
	private Integer places = 1;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "job_user", joinColumns = @JoinColumn(name = "job_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> recruiters;

	private Integer salary;

	@NotNull
	@Column(nullable = false)
	@Field(name = "_title")
	@Field(name = "title", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField(forField = "title")
	private String title;

	public JobOffer() {
		this.jobOfferState = JobOfferState.CREATED;
		this.dateOpened = LocalDate.now();
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
		final JobOffer other = (JobOffer) obj;
		return Objects.equals(this.dateOpened, other.dateOpened) && Objects.equals(this.title, other.title)
				&& Objects.equals(this.places, other.places);
	}

	public Address getAddress() {
		return this.address;
	}

	public Client getClient() {
		return this.client;
	}

	public LocalDate getDateClosed() {
		return this.dateClosed;
	}

	public LocalDate getDateOpened() {
		return this.dateOpened;
	}

	public String getDescription() {
		return this.description;
	}

	public Set<JobCandidature> getJobCandidatures() {
		return this.jobCandidatures;
	}

	public JobOfferState getJobOfferState() {
		return this.jobOfferState;
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

	public String getTitle() {
		return this.title;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.dateOpened, this.title, this.places);
	}

	public boolean hasState(final JobOfferState state) {
		if (this.jobOfferState == null) {
			return state == null;
		} else {
			return this.jobOfferState.equals(state);
		}
	}

	public boolean isOpen() {
		return this.hasState(JobOfferState.OPENED);
	}

	public void setAddress(final Address address) {
		this.address = address;
	}

	public void setClient(final Client client) {
		this.client = client;
	}
	
	public void setDateClosed(final LocalDate dateClosed) {
		this.dateClosed = dateClosed;
	}

	public void setDateOpened(final LocalDate dateOpened) {
		this.dateOpened = dateOpened;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setJobCandidatures(final Set<JobCandidature> jobCandidatures) {
		this.jobCandidatures = jobCandidatures;
	}

	public void setJobOfferState(final JobOfferState jobOfferState) {
		this.jobOfferState = jobOfferState;
	}

	@Override
	public void setOwner(final User owner) {
		this.owner = owner;
	}

	public void setPlaces(final Integer places) {
		this.places = places;
	}

	public void setRecruiters(final List<User> users) {
		this.recruiters = new HashSet<User>();
		if ((users == null) || users.isEmpty()) {
			return;
		}
		for (final User user : users) {
			this.recruiters.add(user);
		}
	}

	public void setRecruiters(final Set<User> recruiters) {
		this.recruiters = recruiters;
	}

	public void setSalary(final Integer salary) {
		this.salary = salary;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return this.title + "-" + this.client.getName();
	}
}
