package es.nivel36.laie.ejb.job.submission;

import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;

import es.nivel36.laie.ejb.core.Event;
import es.nivel36.laie.ejb.core.model.AbstractEntity;
import es.nivel36.laie.ejb.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Indexed
@Table(name = "JOB_SUBMISSION_EVENT")
public class JobSubmissionEvent extends AbstractEntity implements Event {

	private static final long serialVersionUID = -5724212902774335888L;

	@NotNull
	@Column(name = "DATE", nullable = false)
	@GenericField(sortable = Sortable.YES)
	private LocalDateTime date;

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "JOB_SUBMISSION_ID")
	private JobSubmission jobSubmission;

	@Column(name = "NOTES", columnDefinition = "TEXT")
	private String notes;

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "JOB_SUBMISSION_STATE_ID")
	private JobSubmissionState state;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE", length = 16, nullable = false)
	private JobSubmissionEventType type;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = false)
	@IndexedEmbedded(includeDepth = 1)
	private User user;

	public JobSubmissionEvent() {
	}

	public JobSubmissionEvent(final User user, final JobSubmission jobSubmission) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(jobSubmission);
		this.user = user;
		this.date = LocalDateTime.now();
		this.jobSubmission = jobSubmission;
		this.state = jobSubmission.getState();
		this.setType(JobSubmissionEventType.OTHER);
	}

	@Override
	public LocalDateTime getDate() {
		return this.date;
	}

	public JobSubmission getJobSubmission() {
		return this.jobSubmission;
	}

	public String getNotes() {
		return this.notes;
	}

	@Override
	public JobSubmissionState getState() {
		return this.state;
	}

	@Override
	public JobSubmissionEventType getType() {
		return this.type;
	}

	@Override
	public User getUser() {
		return this.user;
	}

	public void setDate(final LocalDateTime date) {
		this.date = date;
	}

	public void setJobSubmission(final JobSubmission jobSubmission) {
		this.jobSubmission = jobSubmission;
	}

	public void setNotes(final String notes) {
		this.notes = notes;
	}

	public void setState(final JobSubmissionState state) {
		this.state = state;
	}

	public void setType(final JobSubmissionEventType type) {
		this.type = type;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj) || (this.getClass() != obj.getClass())) {
			return false;
		}
		final JobSubmissionEvent other = (JobSubmissionEvent) obj;
		return Objects.equals(this.date, other.date) && Objects.equals(this.type, other.type)
				&& Objects.equals(this.user, other.user) && Objects.equals(this.jobSubmission, other.jobSubmission);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.date, this.type, this.user, this.jobSubmission);
	}

	@Override
	public String toString() {
		return "JobSubmissionEvent [date=" + date + ", jobSubmission=" + jobSubmission + ", notes=" + notes + ", state="
				+ state + ", type=" + type + ", user=" + user + "]";
	}
}