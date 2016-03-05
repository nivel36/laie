package ged.ejb.service.job;

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

import ged.ejb.core.i18n.I18n;
import ged.ejb.core.model.AuditedEntity;
import ged.ejb.core.user.User;
import ged.ejb.service.client.Client;

@Entity
public class JobOffer extends AuditedEntity {

	private static final long serialVersionUID = 5579321864799956403L;

	@Column(length = 64)
	private String city;

	@ManyToOne
	@JoinColumn(name = "clientId", nullable = true, updatable = false)
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
	private String description;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "jobOffer", orphanRemoval = true)
	private List<JobCandidature> jobCandidatures = new ArrayList<JobCandidature>();

	@NotNull
	@Column(length = 128, nullable = false)
	private String jobName;

	@NotNull
	private Integer jobPlaces = 1;

	@ManyToOne
	@JoinColumn(name = "recruiterId", nullable = true, updatable = false)
	private User recruiter;

	@Column(length = 64)
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
		if (this.jobName == null) {
			if (other.jobName != null) {
				return false;
			}
		} else if (!this.jobName.equals(other.jobName)) {
			return false;
		}
		if (this.jobPlaces == null) {
			if (other.jobPlaces != null) {
				return false;
			}
		} else if (!this.jobPlaces.equals(other.jobPlaces)) {
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

	public String getJobName() {
		return this.jobName;
	}

	public Integer getJobPlaces() {
		return this.jobPlaces;
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
		result = (prime * result) + ((this.jobName == null) ? 0 : this.jobName.hashCode());
		result = (prime * result) + ((this.jobPlaces == null) ? 0 : this.jobPlaces.hashCode());
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

	public void setJobName(final String jobName) {
		this.jobName = jobName;
	}

	public void setJobPlaces(final Integer jobPlaces) {
		this.jobPlaces = jobPlaces;
	}

	public void setRecruiter(final User recruiter) {
		this.recruiter = recruiter;
	}

	public void setState(final String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return this.jobName + "-" + this.client.getName();
	}
}
