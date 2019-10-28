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
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferState;
import ged.ejb.user.User;

@Entity
@Indexed
public class JobOfferEvent extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Field(name = "date", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField(forField = "date")
	private LocalDateTime date;
	
	@ManyToOne
	private JobOffer jobOffer;

	private JobOfferState state;

	private User user;

	public JobOfferEvent() {
	}

	public JobOfferEvent(final User user, final JobOffer jobOffer) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(jobOffer);
		this.user = user;
		this.jobOffer = jobOffer;
		this.date = LocalDateTime.now();
		this.state = jobOffer.getJobOfferState();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		JobOfferEvent other = (JobOfferEvent) obj;
		return Objects.equals(date, other.date) && Objects.equals(jobOffer, other.jobOffer)
				&& Objects.equals(state, other.state) && Objects.equals(user, other.user);
	}

	public LocalDateTime getDate() {
		return date;
	}

	public JobOffer getJobOffer() {
		return jobOffer;
	}

	public JobOfferState getState() {
		return state;
	}

	public User getUser() {
		return user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(date, jobOffer, state, user);
		return result;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public void setJobOffer(JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	public void setState(JobOfferState state) {
		this.state = state;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
