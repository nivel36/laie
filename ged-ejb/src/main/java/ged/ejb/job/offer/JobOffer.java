package ged.ejb.job.offer;

import java.time.LocalDate;
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
import ged.ejb.core.Address;
import ged.ejb.core.i18n.I18n;
import ged.ejb.core.model.AbstractEntity;
import ged.ejb.core.model.Erasable;
import ged.ejb.core.model.Ownerable;
import ged.ejb.user.User;

@Entity
@Indexed
@Table(name = "JOB_OFFER")
public class JobOffer extends AbstractEntity implements Erasable, Ownerable {

	private static final long serialVersionUID = 5579321864799956403L;

	@Embedded
	@IndexedEmbedded
	private Address address;

	@ManyToOne
	@JoinColumn(name = "clientId", nullable = false)
	@NotNull
	@IndexedEmbedded
	private Client client;

	@I18n
	private String contractDuration;

	@I18n
	private String contractType;

	@Field(analyze = Analyze.NO)
	@SortableField
	private LocalDate dateClosed;

	@Field(analyze = Analyze.NO)
	@SortableField
	private LocalDate dateOpened;

	@Column(nullable = false)
	@Field
	private boolean deleted;

	@Field
	private String description;

	@OneToMany(mappedBy = "jobOffer", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<JobCandidature> jobCandidatures;

	@NotNull
	@ManyToOne
	private JobOfferState jobOfferState;

	@NotNull
	@Column(nullable = false)
	@Field(name = "_name")
	@Field(name = "name", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField(forField = "name")
	private String name;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "ownerId", nullable = false)
	@IndexedEmbedded
	private User owner;

	@NotNull
	private Integer places = 1;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "job_user", joinColumns = @JoinColumn(name = "job_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> recruiters;

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
		return Objects.equals(this.dateOpened, other.dateOpened) && Objects.equals(this.name, other.name)
				&& Objects.equals(this.places, other.places);
	}

	public Address getAddress() {
		return this.address;
	}

	public Client getClient() {
		return this.client;
	}

	public String getContractDuration() {
		return this.contractDuration;
	}

	public String getContractType() {
		return this.contractType;
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

	public String getName() {
		return this.name;
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

	@Override
	public int hashCode() {
		return Objects.hash(this.dateOpened, this.name, this.places);
	}

	@Override
	public boolean isDeleted() {
		return this.deleted;
	}

	public void setAddress(final Address address) {
		this.address = address;
	}

	public void setClient(final Client client) {
		this.client = client;
	}

	public void setContractDuration(final String contractDuration) {
		this.contractDuration = contractDuration;
	}

	public void setContractType(final String contractType) {
		this.contractType = contractType;
	}

	public void setDateClosed(final LocalDate dateClosed) {
		this.dateClosed = dateClosed;
	}

	public void setDateOpened(final LocalDate dateOpened) {
		this.dateOpened = dateOpened;
	}

	@Override
	public void setDeleted(final boolean deleted) {
		this.deleted = deleted;
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

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public void setOwner(final User owner) {
		this.owner = owner;
	}

	public void setPlaces(final Integer places) {
		this.places = places;
	}

	public void setRecruiters(final Set<User> recruiters) {
		this.recruiters = recruiters;
	}

	@Override
	public String toString() {
		return this.name + "-" + this.client.getName();
	}
}
