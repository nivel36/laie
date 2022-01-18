package es.nivel36.laie.ejb.job.meeting;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import es.nivel36.laie.ejb.job.offer.SimpleJobOfferDto;
import es.nivel36.laie.ejb.user.SimpleUserDto;

public class MeetingDto implements Serializable {

	private static final long serialVersionUID = 936092257892612261L;

	private Set<String> attendeesEmails = new HashSet<>();

	private LocalDateTime datePlanned;

	private Duration duration = Duration.ofMinutes(30);

	private SimpleJobOfferDto jobOffer;

	private String location;

	private MeetingType meetingType;

	private SimpleUserDto owner;

	private String result;

	private String title;

	private String uid;

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
		final MeetingDto other = (MeetingDto) obj;
		return Objects.equals(this.datePlanned, other.datePlanned) && Objects.equals(this.title, other.title)
				&& Objects.equals(this.jobOffer, other.jobOffer) && Objects.equals(this.result, other.result);
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

	public SimpleJobOfferDto getJobOffer() {
		return jobOffer;
	}

	public String getLocation() {
		return this.location;
	}

	public MeetingType getMeetingType() {
		return this.meetingType;
	}

	public SimpleUserDto getOwner() {
		return this.owner;
	}

	public String getResult() {
		return this.result;
	}

	public String getTitle() {
		return this.title;
	}

	public String getUid() {
		return uid;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.datePlanned, this.title, this.jobOffer, this.result);
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

	public void setJobOffer(SimpleJobOfferDto jobOffer) {
		this.jobOffer = jobOffer;
	}

	public void setLocation(final String location) {
		this.location = location;
	}

	public void setMeetingType(final MeetingType meetingType) {
		this.meetingType = meetingType;
	}

	public void setOwner(final SimpleUserDto owner) {
		this.owner = owner;
	}

	public void setResult(final String result) {
		this.result = result;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	void setUid(String uid) {
		this.uid = uid;
	}
}
