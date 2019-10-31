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
		final JobOfferEvent other = (JobOfferEvent) obj;
		return Objects.equals(this.date, other.date) && Objects.equals(this.jobOffer, other.jobOffer)
				&& Objects.equals(this.state, other.state) && Objects.equals(this.user, other.user);
	}

	public LocalDateTime getDate() {
		return this.date;
	}

	public JobOffer getJobOffer() {
		return this.jobOffer;
	}

	public JobOfferState getState() {
		return this.state;
	}

	public User getUser() {
		return this.user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = (prime * result) + Objects.hash(this.date, this.jobOffer, this.state, this.user);
		return result;
	}

	public void setDate(final LocalDateTime date) {
		this.date = date;
	}

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	public void setState(final JobOfferState state) {
		this.state = state;
	}

	public void setUser(final User user) {
		this.user = user;
	}
}
