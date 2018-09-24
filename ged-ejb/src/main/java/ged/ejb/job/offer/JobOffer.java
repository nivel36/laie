package ged.ejb.job.offer;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import ged.ejb.client.Client;
import ged.ejb.core.i18n.I18n;
import ged.ejb.core.model.AbstractAuditedEntity;
import ged.ejb.user.User;

@Entity
@Indexed
public class JobOffer extends AbstractAuditedEntity {

	private static final long serialVersionUID = 5579321864799956403L;

	@Column(nullable = false, length = 64)
	@NotNull
	@Field
	private String city;

	@ManyToOne
	@JoinColumn(name = "clientId", nullable = true)
	@IndexedEmbedded
	private Client client;

	@I18n
	private String contractDuration;

	@I18n
	private String contractType;

	@Column(length = 64)
	private String country;

	private LocalDate dateClosed;

	private LocalDate dateOpened;

	@Column(length = 1024)
	@Field
	private String description;

	@OneToMany(mappedBy = "jobOffer", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<JobCandidature> jobCandidatures;

	@NotNull
	@Column(length = 128, nullable = false)
	@Field
	private String name;

	@NotNull
	private Integer places = 1;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "job_user", joinColumns = @JoinColumn(name = "job_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> recruiters;

	@Column(nullable = false, length = 64)
	@NotNull
	private String state;

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
		return Objects.equals(this.dateOpened, other.dateOpened) && Objects.equals(this.name, other.name) && Objects.equals(this.places, other.places);
	}

	public String getCity() {
		return this.city;
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

	public String getCountry() {
		return this.country;
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

	public String getName() {
		return this.name;
	}

	public Integer getPlaces() {
		return this.places;
	}

	public Set<User> getRecruiters() {
		return this.recruiters;
	}

	public String getState() {
		return this.state;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.dateOpened, this.name, this.places);
	}

	public void setCity(final String city) {
		this.city = city;
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

	public void setCountry(final String country) {
		this.country = country;
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

	public void setName(final String name) {
		this.name = name;
	}

	public void setPlaces(final Integer places) {
		this.places = places;
	}

	public void setRecruiters(final Set<User> recruiters) {
		this.recruiters = recruiters;
	}

	public void setState(final String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return this.name + "-" + this.client.getName();
	}
}
