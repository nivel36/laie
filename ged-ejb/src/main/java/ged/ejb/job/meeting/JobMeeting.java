package ged.ejb.job.meeting;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import ged.ejb.core.model.AbstractAuditedEntity;
import ged.ejb.job.offer.JobCandidature;
import ged.ejb.user.User;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "jobCandidatureId", "datePlanned" }) })
public class JobMeeting extends AbstractAuditedEntity {

	private static final long serialVersionUID = 3394583186288921090L;

	@OneToMany
	private List<User> attendees;

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
		if ( obj == null ) {
			return false;
		}
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
		return Objects.equals(this.dateConducted, other.dateConducted)
				&& Objects.equals(this.datePlanned, other.datePlanned)
				&& Objects.equals(this.description, other.description)
				&& Objects.equals(this.jobCandidature, other.jobCandidature)
				&& Objects.equals(this.result, other.result);
	}

	public List<User> getAttendees() {
		return this.attendees;
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
		return Objects.hash(this.dateConducted, this.datePlanned, this.description, this.jobCandidature, this.result);
	}

	public void setAttendees(final List<User> attendees) {
		this.attendees = attendees;
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