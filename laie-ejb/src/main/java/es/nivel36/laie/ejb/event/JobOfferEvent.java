package es.nivel36.laie.ejb.event;

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

import es.nivel36.laie.ejb.core.model.AbstractObfuscableEntity;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.job.offer.JobOfferState;
import es.nivel36.laie.ejb.user.User;

@Entity
@Indexed
public class JobOfferEvent extends AbstractObfuscableEntity {

	private static final long serialVersionUID = 1L;

	@Field(name = "date", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField(forField = "date")
	private LocalDateTime date;

	@ManyToOne
	private JobOffer jobOffer;

	private String notes;

	private JobOfferState state;

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

	public String getNotes() {
		return this.notes;
	}

	public JobOfferState getState() {
		return this.state;
	}

	public User getUser() {
		return this.user;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.date, this.jobOffer, this.state, this.user);
	}

	public void setDate(final LocalDateTime date) {
		this.date = date;
	}

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	public void setNotes(final String notes) {
		this.notes = notes;
	}

	public void setState(final JobOfferState state) {
		this.state = state;
	}

	public void setUser(final User user) {
		this.user = user;
	}
}
