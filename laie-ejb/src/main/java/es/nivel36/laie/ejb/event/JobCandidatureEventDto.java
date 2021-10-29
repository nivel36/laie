package es.nivel36.laie.ejb.event;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import es.nivel36.laie.ejb.job.candidature.JobCandidatureState;
import es.nivel36.laie.ejb.user.SimpleUserDto;

public class JobCandidatureEventDto implements Serializable {

	private static final long serialVersionUID = 6294763438633493823L;

	private LocalDateTime date;

	private String notes;

	private JobCandidatureState state;

	private JobCandidatureEventType type;
	
	private SimpleUserDto user;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JobCandidatureEventDto other = (JobCandidatureEventDto) obj;
		return Objects.equals(date, other.date) && Objects.equals(state, other.state) && type == other.type;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public String getNotes() {
		return notes;
	}

	public JobCandidatureState getState() {
		return state;
	}

	public JobCandidatureEventType getType() {
		return type;
	}

	public SimpleUserDto getUser() {
		return user;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, state, type);
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setState(JobCandidatureState state) {
		this.state = state;
	}
	
	public void setType(JobCandidatureEventType type) {
		this.type = type;
	}

	public void setUser(SimpleUserDto user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "JobCandidatureEventDto [date=" + date + ", state=" + state + ", type=" + type + "]";
	}
}
