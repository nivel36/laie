package ged.ejb.curriculum;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import ged.ejb.core.model.AbstractAuditedEntity;

@Entity
public class JobExperience extends AbstractAuditedEntity {

	private static final long serialVersionUID = -2578992834584255548L;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "curriculumId", nullable = false)
	private Curriculum curriculum;

	@NotNull
	@Column(length = 128, nullable = false)
	private String companyName;

	@NotNull
	@Lob
	@Column( nullable = false)
	private String description;

	@Temporal(TemporalType.DATE)
	private Date fromDate;

	@Column(length = 256)
	private String jobPosition;

	private Boolean stillWorking;

	@Temporal(TemporalType.DATE)
	private Date toDate;

	public String getCompanyName() {
		return companyName;
	}
	
	public Curriculum getCurriculum() {
		return curriculum;
	}

	public String getDescription() {
		return description;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public String getJobPosition() {
		return jobPosition;
	}

	public Date getToDate() {
		return toDate;
	}

	public Boolean getStillWorking() {
		return stillWorking;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public void setCurriculum(Curriculum curriculum) {
		this.curriculum = curriculum;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public void setJobPosition(String jobPosition) {
		this.jobPosition = jobPosition;
	}

	public void setStillWorking(Boolean stillWorking) {
		this.stillWorking = stillWorking;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((companyName == null) ? 0 : companyName.hashCode());
		result = prime * result
				+ ((curriculum == null) ? 0 : curriculum.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((fromDate == null) ? 0 : fromDate.hashCode());
		result = prime * result
				+ ((jobPosition == null) ? 0 : jobPosition.hashCode());
		result = prime * result
				+ ((stillWorking == null) ? 0 : stillWorking.hashCode());
		result = prime * result + ((toDate == null) ? 0 : toDate.hashCode());
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
		JobExperience other = (JobExperience) obj;
		if (companyName == null) {
			if (other.companyName != null)
				return false;
		} else if (!companyName.equals(other.companyName))
			return false;
		if (curriculum == null) {
			if (other.curriculum != null)
				return false;
		} else if (!curriculum.equals(other.curriculum))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (fromDate == null) {
			if (other.fromDate != null)
				return false;
		} else if (!fromDate.equals(other.fromDate))
			return false;
		if (jobPosition == null) {
			if (other.jobPosition != null)
				return false;
		} else if (!jobPosition.equals(other.jobPosition))
			return false;
		if (stillWorking == null) {
			if (other.stillWorking != null)
				return false;
		} else if (!stillWorking.equals(other.stillWorking))
			return false;
		if (toDate == null) {
			if (other.toDate != null)
				return false;
		} else if (!toDate.equals(other.toDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JobExperience [companyName=" + companyName + ", description="
				+ description + ", fromDate=" + fromDate + ", jobPosition="
				+ jobPosition + ", stillWorking=" + stillWorking + ", toDate="
				+ toDate + "]";
	}
}
