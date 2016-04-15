package ged.ejb.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import ged.ejb.client.Client;
import ged.ejb.core.i18n.I18n;
import ged.ejb.core.model.AuditedEntity;
import ged.ejb.user.User;

@Entity
@Indexed
public class JobOffer extends AuditedEntity {

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

	@NotNull
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dateClosed;

	@Temporal(TemporalType.DATE)
	private Date dateOpened;

	@Column(length = 1024)
	@Field
	private String description;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "jobOffer", orphanRemoval = true)
	private List<JobCandidature> jobCandidatures = new ArrayList<JobCandidature>();

	@NotNull
	@Column(length = 128, nullable = false)
	@Field
	private String name;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "ownerId", nullable = false)
	private User owner;

	@NotNull
	private Integer places = 1;

	@ManyToOne
	@JoinColumn(name = "recruiterId", nullable = true)
	private User recruiter;

	@Column(nullable = false, length = 64)
	@NotNull
	private String state;

	public void addJobCandidature(final JobCandidature jobCandidature) {
		if (jobCandidature == null) {
			throw new NullPointerException();
		}
		jobCandidature.setJobOffer(this);
		this.jobCandidatures.add(jobCandidature);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final JobOffer other = (JobOffer) obj;
		if (this.dateOpened == null) {
			if (other.dateOpened != null) {
				return false;
			}
		} else if (!this.dateOpened.equals(other.dateOpened)) {
			return false;
		}
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		if (this.places == null) {
			if (other.places != null) {
				return false;
			}
		} else if (!this.places.equals(other.places)) {
			return false;
		}
		return true;
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

	public Date getDateClosed() {
		return this.dateClosed;
	}

	public Date getDateOpened() {
		return this.dateOpened;
	}

	public String getDescription() {
		return this.description;
	}

	public List<JobCandidature> getJobCandidatures() {
		return this.jobCandidatures;
	}

	public String getName() {
		return this.name;
	}

	public User getOwner() {
		return this.owner;
	}

	public Integer getPlaces() {
		return this.places;
	}

	public User getRecruiter() {
		return this.recruiter;
	}

	public String getState() {
		return this.state;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.dateOpened == null) ? 0 : this.dateOpened.hashCode());
		result = (prime * result) + ((this.name == null) ? 0 : this.name.hashCode());
		result = (prime * result) + ((this.places == null) ? 0 : this.places.hashCode());
		return result;
	}

	public void removeJobCandidature(final JobCandidature jobCandidature) {
		if (jobCandidature == null) {
			throw new NullPointerException();
		}
		this.jobCandidatures.remove(jobCandidature);
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

	public void setDateClosed(final Date dateClosed) {
		this.dateClosed = dateClosed;
	}

	public void setDateOpened(final Date dateOpened) {
		this.dateOpened = dateOpened;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setJobCandidatures(final List<JobCandidature> jobCandidatures) {
		this.jobCandidatures = jobCandidatures;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setOwner(final User owner) {
		this.owner = owner;
	}

	public void setPlaces(final Integer places) {
		this.places = places;
	}

	public void setRecruiter(final User recruiter) {
		this.recruiter = recruiter;
	}

	public void setState(final String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return this.name + "-" + this.client.getName();
	}
}
