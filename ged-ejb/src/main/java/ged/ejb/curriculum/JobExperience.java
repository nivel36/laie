package ged.ejb.curriculum;

import java.util.Date;
import java.util.Objects;

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
	@Column(nullable = false)
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
		return Objects.hash(companyName, curriculum, description, fromDate, jobPosition, stillWorking, toDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		JobExperience other = (JobExperience) obj;
		return Objects.equals(this.companyName, other.companyName) && Objects.equals(this.curriculum, other.curriculum)
				&& Objects.equals(this.description, other.description) && Objects.equals(this.fromDate, other.fromDate)
				&& Objects.equals(this.jobPosition, other.jobPosition)
				&& Objects.equals(this.stillWorking, other.stillWorking) && Objects.equals(this.toDate, other.toDate);
	}

	@Override
	public String toString() {
		return "JobExperience [companyName=" + companyName + ", description=" + description + ", fromDate=" + fromDate
				+ ", jobPosition=" + jobPosition + ", stillWorking=" + stillWorking + ", toDate=" + toDate + "]";
	}
}
