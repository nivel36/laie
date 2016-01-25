package ged.ejb.service.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import ged.ejb.core.i18n.I18n;
import ged.ejb.core.model.AuditedEntity;

@Entity
public class JobOffer extends AuditedEntity {

	private static final long serialVersionUID = 5579321864799956403L;

	@Column(length = 64)
	private String city;

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

	@Column(length = 64)
	private String state;

	public void addJobCandidature(JobCandidature jobCandidature) {
		if (jobCandidature == null) {
			throw new NullPointerException();
		}
		jobCandidature.setJobOffer(this);
		jobCandidatures.add(jobCandidature);
	}

	public String getCity() {
		return city;
	}

	public String getContractDuration() {
		return contractDuration;
	}

	public String getContractType() {
		return contractType;
	}

	public String getCountry() {
		return country;
	}

	public Date getDateClosed() {
		return dateClosed;
	}

	public Date getDateOpened() {
		return dateOpened;
	}

	public String getDescription() {
		return description;
	}

	public List<JobCandidature> getJobCandidatures() {
		return jobCandidatures;
	}

	public String getJobName() {
		return jobName;
	}

	public Integer getJobPlaces() {
		return jobPlaces;
	}

	public String getState() {
		return state;
	}

	public void removeJobCandidature(JobCandidature jobCandidature) {
		if (jobCandidature == null) {
			throw new NullPointerException();
		}
		jobCandidatures.remove(jobCandidature);
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setContractDuration(String contractDuration) {
		this.contractDuration = contractDuration;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setDateClosed(Date dateClosed) {
		this.dateClosed = dateClosed;
	}

	public void setDateOpened(Date dateOpened) {
		this.dateOpened = dateOpened;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setJobCandidatures(List<JobCandidature> jobCandidatures) {
		this.jobCandidatures = jobCandidatures;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public void setJobPlaces(Integer jobPlaces) {
		this.jobPlaces = jobPlaces;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dateOpened == null) ? 0 : dateOpened.hashCode());
		result = prime * result + ((jobName == null) ? 0 : jobName.hashCode());
		result = prime * result
				+ ((jobPlaces == null) ? 0 : jobPlaces.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JobOffer other = (JobOffer) obj;
		if (dateOpened == null) {
			if (other.dateOpened != null)
				return false;
		} else if (!dateOpened.equals(other.dateOpened))
			return false;
		if (jobName == null) {
			if (other.jobName != null)
				return false;
		} else if (!jobName.equals(other.jobName))
			return false;
		if (jobPlaces == null) {
			if (other.jobPlaces != null)
				return false;
		} else if (!jobPlaces.equals(other.jobPlaces))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JobOffer [dateClosed=" + dateClosed + ", dateOpened="
				+ dateOpened + ", jobName=" + jobName + ", jobPlaces="
				+ jobPlaces + "]";
	}
}
