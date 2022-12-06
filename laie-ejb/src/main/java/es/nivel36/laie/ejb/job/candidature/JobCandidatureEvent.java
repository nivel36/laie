package es.nivel36.laie.ejb.job.candidature;

import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

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
@Table(name = "JOB_CANDIDATURE_EVENT")
public class JobCandidatureEvent extends AbstractEntity implements Event {

	private static final long serialVersionUID = -5724212902774335888L;

	@KeywordField(sortable=Sortable.YES)
	@Column(name = "DATE")
	private LocalDateTime date;

	@ManyToOne(optional = false)
	@JoinColumn(name = "JOB_CANDIDATURE_ID")
	private JobCandidature jobCandidature;

	@Column(name = "NOTES", columnDefinition = "TEXT")
	private String notes;

	@ManyToOne
	@IndexedEmbedded
	@JoinColumn(name = "JOB_CANDIDATURE_STATE_ID")
	private JobCandidatureState state;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE", nullable = false, length = 16)
	private JobCandidatureEventType type;

	@ManyToOne
	@IndexedEmbedded
	@JoinColumn(name = "USER_ID")
	private User user;

	public JobCandidatureEvent() {
	}

	public JobCandidatureEvent(final User user, final JobCandidature jobCandidature) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(jobCandidature);
		this.user = user;
		this.date = LocalDateTime.now();
		this.jobCandidature = jobCandidature;
		this.state = jobCandidature.getState();
		this.setType(JobCandidatureEventType.OTHER);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final JobCandidatureEvent other = (JobCandidatureEvent) obj;
		return Objects.equals(this.date, other.date) && Objects.equals(this.type, other.type)
				&& Objects.equals(this.user, other.user) && Objects.equals(this.jobCandidature, other.jobCandidature);
	}

	public LocalDateTime getDate() {
		return this.date;
	}

	public JobCandidature getJobCandidature() {
		return this.jobCandidature;
	}

	public String getNotes() {
		return this.notes;
	}

	public JobCandidatureState getState() {
		return this.state;
	}

	public JobCandidatureEventType getType() {
		return this.type;
	}

	public User getUser() {
		return this.user;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.date, this.type, this.user, this.jobCandidature);
	}

	public void setDate(final LocalDateTime date) {
		this.date = date;
	}

	public void setJobCandidature(final JobCandidature jobCandidature) {
		this.jobCandidature = jobCandidature;
	}

	public void setNotes(final String notes) {
		this.notes = notes;
	}

	public void setState(final JobCandidatureState state) {
		this.state = state;
	}

	public void setType(final JobCandidatureEventType type) {
		this.type = type;
	}

	public void setUser(final User user) {
		this.user = user;
	}
}