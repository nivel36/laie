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
public class JobCandidatureEvent extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Field(name = "date", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField(forField = "date")
	private LocalDateTime date;

	private String description;

	@ManyToOne
	private JobCandidature jobCandidature;

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
		this.state = jobCandidature.getJobCandidatureState();
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
				&& Objects.equals(this.user, other.user) && Objects.equals(this.jobCandidature, this.jobCandidature);
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

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setJobCandidature(final JobCandidature jobCandidature) {
		this.jobCandidature = jobCandidature;
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