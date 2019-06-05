package ged.ejb.job.meeting;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.IndexedEmbedded;

import ged.ejb.candidate.Candidate;
import ged.ejb.client.Contact;
import ged.ejb.core.model.AbstractEntity;
import ged.ejb.core.model.Ownerable;
import ged.ejb.job.candidature.JobCandidature;
import ged.ejb.person.Person;
import ged.ejb.user.User;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "jobCandidatureId", "datePlanned" }) })
public class Meeting extends AbstractEntity implements Ownerable {

	private static final long serialVersionUID = 3394583186288921090L;

	@OneToMany
	private Set<Candidate> candidateAttendees = new HashSet<>();

	@OneToMany
	private Set<Contact> contactAttendees = new HashSet<>();

	public Set<Contact> getContactAttendees() {
		return contactAttendees;
	}

	public void setContactAttendees(Set<Contact> contactAttendees) {
		this.contactAttendees = contactAttendees;
	}

	@NotNull
	@Column(nullable = false)
	private LocalDateTime datePlanned;

	private String description;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "jobCandidatureId", nullable = false)
	private JobCandidature jobCandidature;

	private String location;

	private MeetingType meetingType;

	@ElementCollection
	private Set<String> otherAttendees = new HashSet<>();

	@NotNull
	@ManyToOne
	@JoinColumn(name = "ownerId", nullable = false)
	@IndexedEmbedded
	private User owner;

	private String result;

	@OneToMany
	private Set<User> userAttendees = new HashSet<>();

	public void addAttendee(final Person person) {
		if (person instanceof User) {
			userAttendees.add((User) person);
		} else if (person instanceof Candidate) {
			candidateAttendees.add((Candidate) person);
		} else if (person instanceof Contact) {
			contactAttendees.add((Contact) person);
		}
	}

	public void addOtherAttendee(final String name) {
		otherAttendees.add(name);
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
		return Objects.equals(this.datePlanned, other.datePlanned)
				&& Objects.equals(this.description, other.description)
				&& Objects.equals(this.jobCandidature, other.jobCandidature)
				&& Objects.equals(this.result, other.result);
	}
	
	public Set<Candidate> getCandidateAttendees() {
		return candidateAttendees;
	}
	
	public LocalDateTime getDatePlanned() {
		return this.datePlanned;
	}

	public String getDescription() {
		return this.description;
	}

	public JobCandidature getJobCandidature() {
		return this.jobCandidature;
	}

	public MeetingType getJobMeetingType() {
		return this.meetingType;
	}

	public String getLocation() {
		return this.location;
	}

	public MeetingType getMeetingType() {
		return this.meetingType;
	}

	public Set<String> getOtherAttendees() {
		return otherAttendees;
	}

	@Override
	public User getOwner() {
		return this.owner;
	}

	public String getResult() {
		return this.result;
	}

	public Set<User> getUserAttendees() {
		return userAttendees;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.datePlanned, this.description, this.jobCandidature, this.result);
	}

	public void removeAttendee(final Person person) {
		if (person instanceof User) {
			userAttendees.remove((User) person);
		} else if (person instanceof Candidate) {
			candidateAttendees.remove((Candidate) person);
		} else if (person instanceof Contact) {
			contactAttendees.remove((Contact) person);
		}
	}

	public void removeOtherAttendee(final String name) {
		otherAttendees.remove(name);
	}

	public void setCandidateAttendees(Set<Candidate> candidateAttendees) {
		this.candidateAttendees = candidateAttendees;
	}

	public void setDatePlanned(final LocalDateTime datePlanned) {
		this.datePlanned = datePlanned;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setJobCandidature(final JobCandidature jobCandidature) {
		this.jobCandidature = jobCandidature;
	}

	public void setJobMeetingType(final MeetingType meetingType) {
		this.meetingType = meetingType;
	}

	public void setLocation(final String location) {
		this.location = location;
	}

	public void setMeetingType(final MeetingType meetingType) {
		this.meetingType = meetingType;
	}

	public void setOtherAttendees(Set<String> otherAttendees) {
		this.otherAttendees = otherAttendees;
	}

	@Override
	public void setOwner(final User owner) {
		this.owner = owner;
	}

	public void setResult(final String result) {
		this.result = result;
	}

	public void setUserAttendees(Set<User> userAttendees) {
		this.userAttendees = userAttendees;
	}
}