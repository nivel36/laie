package es.nivel36.laie.ejb.event;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import es.nivel36.laie.ejb.job.offer.JobOfferState;
import es.nivel36.laie.ejb.user.SimpleUserDto;

public class JobOfferEventDto implements Serializable {
	
	private static final long serialVersionUID = -5704400180546665572L;

	private LocalDateTime date;

	private String notes;

	private JobOfferState state;

	private SimpleUserDto user;

	@Override
	public int hashCode() {
		return Objects.hash(date, state, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JobOfferEventDto other = (JobOfferEventDto) obj;
		return Objects.equals(date, other.date) && state == other.state && Objects.equals(user, other.user);
	}

	public LocalDateTime getDate() {
		return date;
	}

	void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public JobOfferState getState() {
		return state;
	}

	public void setState(JobOfferState state) {
		this.state = state;
	}

	SimpleUserDto getUser() {
		return user;
	}

	public void setUser(SimpleUserDto user) {
		this.user = user;
	}
}
