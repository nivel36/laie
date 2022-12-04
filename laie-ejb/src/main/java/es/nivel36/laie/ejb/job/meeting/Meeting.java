package es.nivel36.laie.ejb.job.meeting;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;

import es.nivel36.laie.ejb.core.model.AbstractEntity;
import es.nivel36.laie.ejb.core.model.Ownerable;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.user.User;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "MEETING")
public class Meeting extends AbstractEntity implements Ownerable {

	private static final long serialVersionUID = -8068167269155086050L;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "EMAILS", joinColumns = @JoinColumn(name = "MEETING_ID"))
	@Column(name = "EMAIL")
	private Set<String> attendeesEmails = new HashSet<>();

	@NotNull
	@Column(name = "DATE_PLANNED", nullable = false)
	private LocalDateTime datePlanned;

	@NotNull
	@Column(name = "DURATION", nullable = false)
	private Duration duration = Duration.ofMinutes(30);

	@ManyToOne
	@JoinColumn(name = "JOB_OFFER_ID")
	private JobOffer jobOffer;

	@Column(name = "LOCATION")
	private String location;

	@Enumerated(EnumType.STRING)
	@Column(name = "MEETING_TYPE")
	private MeetingType meetingType;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "OWNER_ID", nullable = false)
	@IndexedEmbedded
	private User owner;

	@Column(name = "DESCRIPTION", columnDefinition = "TEXT")
	private String description;

	@NotNull
	@Column(name = "TITLE", nullable = false)
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
				&& Objects.equals(this.jobOffer, other.jobOffer) && Objects.equals(this.description, other.description);
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

	public JobOffer getJobOffer() {
		return this.jobOffer;
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

	public String getDescription() {
		return this.description;
	}

	public String getTitle() {
		return this.title;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.datePlanned, this.title, this.jobOffer, this.description);
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

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
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

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setTitle(final String title) {
		this.title = title;
	}
}