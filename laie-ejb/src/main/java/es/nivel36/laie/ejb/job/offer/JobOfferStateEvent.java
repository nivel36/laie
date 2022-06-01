package es.nivel36.laie.ejb.job.offer;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.search.annotations.IndexedEmbedded;

import es.nivel36.laie.ejb.core.Event;
import es.nivel36.laie.ejb.core.model.AbstractEntity;
import es.nivel36.laie.ejb.user.User;

@Entity
public class JobOfferStateEvent extends AbstractEntity implements Event {

	private static final long serialVersionUID = 3586854275179907036L;

	private LocalDateTime date;

	@ManyToOne
	private JobOffer jobOffer;

	private String notes;

	private JobOfferState state;

	private JobOfferStateEventType type;

	@ManyToOne
	@IndexedEmbedded
	private User user;

	@Override
	public LocalDateTime getDate() {
		return date;
	}

	public JobOffer getJobOffer() {
		return jobOffer;
	}

	public String getNotes() {
		return notes;
	}

	@Override
	public JobOfferState getState() {
		return state;
	}

	@Override
	public JobOfferStateEventType getType() {
		return type;
	}

	@Override
	public User getUser() {
		return user;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public void setJobOffer(JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setState(JobOfferState state) {
		this.state = state;
	}

	public void setType(JobOfferStateEventType type) {
		this.type = type;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		JobOfferStateEvent other = (JobOfferStateEvent) obj;
		return Objects.equals(date, other.date) && Objects.equals(jobOffer, other.jobOffer) && state == other.state;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(date, jobOffer, state);
	}
}
