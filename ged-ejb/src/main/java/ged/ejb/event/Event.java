package ged.ejb.event;

import java.time.LocalDateTime;

import javax.persistence.Entity;

import ged.ejb.core.model.AbstractEntity;
import ged.ejb.job.candidature.JobCandidature;

@Entity
public class Event extends AbstractEntity {

	private static final long serialVersionUID = 231213611773600407L;

	private LocalDateTime eventDateTime;

	private JobCandidature jobCandidature;

	private String description;

	private EventType eventType;

	public String getDescription() {
		return this.description;
	}

	public LocalDateTime getEventDateTime() {
		return this.eventDateTime;
	}

	public EventType getEventType() {
		return this.eventType;
	}

	public JobCandidature getJobCandidature() {
		return this.jobCandidature;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setEventDateTime(final LocalDateTime eventDateTime) {
		this.eventDateTime = eventDateTime;
	}

	public void setEventType(final EventType eventType) {
		this.eventType = eventType;
	}

	public void setJobCandidature(final JobCandidature jobCandidature) {
		this.jobCandidature = jobCandidature;
	}
}
