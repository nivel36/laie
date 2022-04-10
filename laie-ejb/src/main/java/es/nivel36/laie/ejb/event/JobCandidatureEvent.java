package es.nivel36.laie.ejb.event;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.SortableField;
import org.hibernate.search.annotations.Store;

import es.nivel36.laie.ejb.core.Event;
import es.nivel36.laie.ejb.core.model.AbstractEntity;
import es.nivel36.laie.ejb.job.candidature.JobCandidature;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureState;
import es.nivel36.laie.ejb.user.User;

@Entity
@Indexed
public class JobCandidatureEvent extends AbstractEntity implements Event {

	private static final long serialVersionUID = -5724212902774335888L;

	@Field(name = "date", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField(forField = "date")
	private LocalDateTime date;

	@ManyToOne
	private JobCandidature jobCandidature;

	private String notes;

	@ManyToOne
	@IndexedEmbedded
	private JobCandidatureState state;

	private JobCandidatureEventType type;

	@ManyToOne
	@IndexedEmbedded
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