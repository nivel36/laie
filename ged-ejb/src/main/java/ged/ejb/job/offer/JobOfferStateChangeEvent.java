package ged.ejb.job.offer;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.SortableField;
import org.hibernate.search.annotations.Store;

import ged.ejb.core.model.AbstractEntity;
import ged.ejb.user.User;

@Entity
public class JobOfferStateChangeEvent extends AbstractEntity {

	private static final long serialVersionUID = -6400237230027216957L;

	public JobOfferStateChangeEvent() {
		this.date = LocalDateTime.now();
	}

	public JobOfferStateChangeEvent(JobOffer jobOffer, JobOfferState jobOfferState) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(jobOfferState);
		this.date = LocalDateTime.now();
		this.jobOffer = jobOffer;
		this.jobOfferState = jobOfferState;
	}

	@Field(name = "date", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField(forField = "date")
	private LocalDateTime date;

	@ManyToOne
	private JobOffer jobOffer;

	private JobOfferState jobOfferState;

	private String notes;

	@ManyToOne
	@IndexedEmbedded
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
		final JobOfferStateChangeEvent other = (JobOfferStateChangeEvent) obj;
		return Objects.equals(this.date, other.date) && Objects.equals(this.jobOffer, other.jobOffer);
	}

	public LocalDateTime getDate() {
		return this.date;
	}

	public JobOffer getJobOffer() {
		return this.jobOffer;
	}

	public JobOfferState getJobOfferState() {
		return this.jobOfferState;
	}

	public String getNotes() {
		return this.notes;
	}

	public User getUser() {
		return this.user;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.date, this.jobOffer);
	}

	public void setDate(final LocalDateTime date) {
		this.date = date;
	}

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	public void setJobOfferState(final JobOfferState jobOfferState) {
		this.jobOfferState = jobOfferState;
	}

	public void setNotes(final String notes) {
		this.notes = notes;
	}

	public void setUser(final User user) {
		this.user = user;
	}
}
