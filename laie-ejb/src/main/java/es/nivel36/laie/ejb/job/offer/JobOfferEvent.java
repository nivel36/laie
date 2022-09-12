package es.nivel36.laie.ejb.job.offer;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.IndexedEmbedded;

import es.nivel36.laie.ejb.core.Event;
import es.nivel36.laie.ejb.core.model.AbstractEntity;
import es.nivel36.laie.ejb.user.User;

@Table(name = "JOB_OFFER_EVENT")
@Entity
public class JobOfferEvent extends AbstractEntity implements Event {

	private static final long serialVersionUID = 3586854275179907036L;

	@NotNull
	@Column(name="DATE")
	private LocalDateTime date;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "JOB_OFFER_ID", nullable = false)
	private JobOffer jobOffer;

	@Column(name="NOTES")
	private String notes;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name="STATE")
	private JobOfferState state;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name="TYPE")
	private JobOfferEventType type;

	@ManyToOne
	@JoinColumn(name = "USER_ID")
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
	public JobOfferEventType getType() {
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

	public void setType(JobOfferEventType type) {
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
		JobOfferEvent other = (JobOfferEvent) obj;
		return Objects.equals(date, other.date) && Objects.equals(jobOffer, other.jobOffer) && state == other.state;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(date, jobOffer, state);
	}
}
