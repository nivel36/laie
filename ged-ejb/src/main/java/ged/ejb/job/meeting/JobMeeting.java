package ged.ejb.job.meeting;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import ged.ejb.core.model.AbstractAuditedEntity;
import ged.ejb.job.JobCandidature;

@NamedQueries({
		@NamedQuery(name = "JobMeeting.getConductedJobMeetings", query = "SELECT j FROM JobMeeting j WHERE j.jobCandidature.jobOffer = :jobOffer AND j.dateConducted IS NOT NULL"),
		@NamedQuery(name = "JobMeeting.getPlannedJobMeetings", query = "SELECT j FROM JobMeeting j WHERE j.jobCandidature.jobOffer = :jobOffer AND j.dateConducted IS NULL") })
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {
		"jobCandidatureId", "datePlanned" }) })
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

	public Date getDateConducted() {
		return dateConducted;
	}

	public Date getDatePlanned() {
		return datePlanned;
	}

	public String getDescription() {
		return description;
	}

	public JobCandidature getJobCandidature() {
		return jobCandidature;
	}

	public String getResult() {
		return result;
	}

	public void setDateConducted(Date dateConducted) {
		this.dateConducted = dateConducted;
	}

	public void setDatePlanned(Date datePlanned) {
		this.datePlanned = datePlanned;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setJobCandidature(JobCandidature jobCandidature) {
		this.jobCandidature = jobCandidature;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
