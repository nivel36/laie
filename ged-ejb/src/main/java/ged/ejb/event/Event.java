package ged.ejb.event;

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

import ged.ejb.core.model.AbstractEntity;
import ged.ejb.job.candidature.JobCandidature;
import ged.ejb.job.candidature.JobCandidatureState;
import ged.ejb.user.User;

@Entity
@Indexed
public class Event extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	public Event() {
	}

	public Event(User user, JobCandidature jobCandidature) {
		this.user = user;
		this.date = LocalDateTime.now();
		if (jobCandidature != null) {
			this.jobCandidature = jobCandidature;
			this.status = jobCandidature.getJobCandidatureState();
		}
	}

	@Field(name = "date", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField(forField = "date")
	private LocalDateTime date;

	private String description;

	@ManyToOne
	private JobCandidature jobCandidature;

	@ManyToOne
	@IndexedEmbedded
	private JobCandidatureState status;

	private EventType type;

	@ManyToOne
	private User user;

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
		final Event other = (Event) obj;
		return Objects.equals(this.date, other.date) && Objects.equals(this.type, other.type)
				&& Objects.equals(this.user, other.user);
	}

	public LocalDateTime getDate() {
		return this.date;
	}

	public String getDescription() {
		return this.description;
	}

	public JobCandidature getJobCandidature() {
		return this.jobCandidature;
	}

	public JobCandidatureState getStatus() {
		return this.status;
	}

	public EventType getType() {
		return this.type;
	}

	public User getUser() {
		return this.user;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.date, this.type, this.user);
	}

	public void setDate(final LocalDateTime date) {
		this.date = date;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setJobCandidature(final JobCandidature jobCandidature) {
		this.jobCandidature = jobCandidature;
	}

	public void setStatus(final JobCandidatureState status) {
		this.status = status;
	}

	public void setType(final EventType type) {
		this.type = type;
	}

	public void setUser(final User user) {
		this.user = user;
	}
}
