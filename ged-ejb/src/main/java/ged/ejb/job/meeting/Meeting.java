package ged.ejb.job.meeting;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.IndexedEmbedded;

import ged.ejb.core.model.AbstractIndexedEntity;
import ged.ejb.core.model.Ownerable;
import ged.ejb.job.candidature.JobCandidature;
import ged.ejb.user.User;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "jobCandidatureId", "datePlanned" }) })
public class Meeting extends AbstractIndexedEntity implements Ownerable {

	private static final long serialVersionUID = 1L;

	@ElementCollection
	@CollectionTable(name = "emails", joinColumns = @JoinColumn(name = "meeting_id"))
	@Column(name = "email")
	private Set<String> attendeesEmails = new HashSet<>();

	@NotNull
	@Column(nullable = false)
	private LocalDateTime datePlanned;
	
	@NotNull
	@Column(nullable = false)
	private Duration duration = Duration.ofMinutes(30);

	@ManyToOne
	@JoinColumn(name = "jobCandidatureId")
	private JobCandidature jobCandidature;

	private String location;

	private MeetingType meetingType;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "ownerId", nullable = false)
	@IndexedEmbedded
	private User owner;

	private String result;

	@NotNull
	@Column(nullable = false)
	private String title;

	public void addAttendee(final String email) {
		Objects.requireNonNull(email, "Email can't be null");
		this.attendeesEmails.add(email);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Meeting other = (Meeting) obj;
		return Objects.equals(this.datePlanned, other.datePlanned) && Objects.equals(this.title, other.title)
				&& Objects.equals(this.jobCandidature, other.jobCandidature)
				&& Objects.equals(this.result, other.result);
	}

	public Set<String> getAttendeesEmails() {
		return this.attendeesEmails;
	}

	public LocalDateTime getDatePlanned() {
		return this.datePlanned;
	}

	public Duration getDuration() {
		return duration;
	}

	public JobCandidature getJobCandidature() {
		return this.jobCandidature;
	}

	public String getLocation() {
		return this.location;
	}

	public MeetingType getMeetingType() {
		return this.meetingType;
	}

	@Override
	public User getOwner() {
		return this.owner;
	}

	public String getResult() {
		return this.result;
	}

	public String getTitle() {
		return this.title;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.datePlanned, this.title, this.jobCandidature, this.result);
	}

	public void removeAttendee(final String email) {
		Objects.requireNonNull(email, "Email can't be null");
		this.attendeesEmails.remove(email);
	}

	public void setAttendeesEmails(final Set<String> attendeesEmails) {
		this.attendeesEmails = attendeesEmails;
	}

	public void setDatePlanned(final LocalDateTime datePlanned) {
		this.datePlanned = datePlanned;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public void setJobCandidature(final JobCandidature jobCandidature) {
		this.jobCandidature = jobCandidature;
	}

	public void setLocation(final String location) {
		this.location = location;
	}

	public void setMeetingType(final MeetingType meetingType) {
		this.meetingType = meetingType;
	}

	@Override
	public void setOwner(final User owner) {
		this.owner = owner;
	}

	public void setResult(final String result) {
		this.result = result;
	}

	public void setTitle(final String title) {
		this.title = title;
	}
}