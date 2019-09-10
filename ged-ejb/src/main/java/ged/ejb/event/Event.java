package ged.ejb.event;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.SortableField;
import org.hibernate.search.annotations.Store;

import ged.ejb.core.model.AbstractEntity;
import ged.ejb.job.candidature.JobCandidature;
import ged.ejb.user.User;

@Entity
@Indexed
public class Event extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;

	@Field(name = "date", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField(forField = "date")
	private LocalDateTime date;
	
	private String description;

	@ManyToOne
	private JobCandidature jobCandidature;

	@ManyToOne
	private EventType type;
	
	@ManyToOne
	private User user;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		return Objects.equals(date, other.date) && Objects.equals(type, other.type) && Objects.equals(user, other.user);
	}

	public LocalDateTime getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}

	public JobCandidature getJobCandidature() {
		return jobCandidature;
	}

	public EventType getType() {
		return type;
	}

	public User getUser() {
		return user;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, type, user);
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setJobCandidature(JobCandidature jobCandidature) {
		this.jobCandidature = jobCandidature;
	}

	public void setType(EventType type) {
		this.type = type;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}
