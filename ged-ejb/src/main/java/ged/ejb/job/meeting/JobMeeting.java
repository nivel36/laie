package ged.ejb.job.meeting;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import ged.ejb.core.model.AbstractAuditedEntity;
import ged.ejb.job.offer.JobCandidature;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "jobCandidatureId", "datePlanned" }) })
public class JobMeeting extends AbstractAuditedEntity {

	private static final long serialVersionUID = 3394583186288921090L;

	@Temporal(TemporalType.TIME)
	private Date dateConducted;

	@NotNull
	@Temporal(TemporalType.TIME)
	@Column(nullable = false)
	private Date datePlanned;

	@Column(length = 1024)
	private String description;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "jobCandidatureId", nullable = false)
	private JobCandidature jobCandidature;

	@NotNull
	@Column(length = 128, nullable = false)
	private String result;

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final JobMeeting other = (JobMeeting) obj;
		if (this.dateConducted == null) {
			if (other.dateConducted != null) {
				return false;
			}
		} else if (!this.dateConducted.equals(other.dateConducted)) {
			return false;
		}
		if (this.datePlanned == null) {
			if (other.datePlanned != null) {
				return false;
			}
		} else if (!this.datePlanned.equals(other.datePlanned)) {
			return false;
		}
		if (this.description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!this.description.equals(other.description)) {
			return false;
		}
		if (this.jobCandidature == null) {
			if (other.jobCandidature != null) {
				return false;
			}
		} else if (!this.jobCandidature.equals(other.jobCandidature)) {
			return false;
		}
		if (this.result == null) {
			if (other.result != null) {
				return false;
			}
		} else if (!this.result.equals(other.result)) {
			return false;
		}
		return true;
	}

	public Date getDateConducted() {
		return this.dateConducted;
	}

	public Date getDatePlanned() {
		return this.datePlanned;
	}

	public String getDescription() {
		return this.description;
	}

	public JobCandidature getJobCandidature() {
		return this.jobCandidature;
	}

	public String getResult() {
		return this.result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (this.dateConducted == null ? 0 : this.dateConducted.hashCode());
		result = prime * result + (this.datePlanned == null ? 0 : this.datePlanned.hashCode());
		result = prime * result + (this.description == null ? 0 : this.description.hashCode());
		result = prime * result + (this.jobCandidature == null ? 0 : this.jobCandidature.hashCode());
		result = prime * result + (this.result == null ? 0 : this.result.hashCode());
		return result;
	}

	public void setDateConducted(final Date dateConducted) {
		this.dateConducted = dateConducted;
	}

	public void setDatePlanned(final Date datePlanned) {
		this.datePlanned = datePlanned;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setJobCandidature(final JobCandidature jobCandidature) {
		this.jobCandidature = jobCandidature;
	}

	public void setResult(final String result) {
		this.result = result;
	}
}