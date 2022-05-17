package es.nivel36.laie.ejb.job.offer;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import es.nivel36.laie.ejb.core.model.AbstractEntity;
import es.nivel36.laie.ejb.user.User;

@Entity
public class JobOfferStateEvent extends AbstractEntity {
	
	private static final long serialVersionUID = 3586854275179907036L;

	private LocalDateTime date;

	@ManyToOne
	private JobOffer jobOffer;

	private String notes;

	private JobOfferState previous;

	private JobOfferState state;

	@ManyToOne
	private User user;

	public LocalDateTime getDate() {
		return date;
	}

	public JobOffer getJobOffer() {
		return jobOffer;
	}

	public String getNotes() {
		return notes;
	}

	public JobOfferState getPrevious() {
		return previous;
	}

	public JobOfferState getState() {
		return state;
	}

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

	public void setPrevious(JobOfferState previous) {
		this.previous = previous;
	}

	public void setState(JobOfferState state) {
		this.state = state;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, jobOffer, state);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JobOfferStateEvent other = (JobOfferStateEvent) obj;
		return Objects.equals(date, other.date) && Objects.equals(jobOffer, other.jobOffer) && state == other.state;
	}
}
