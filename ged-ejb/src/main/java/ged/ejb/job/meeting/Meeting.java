package ged.ejb.job.meeting;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.IndexedEmbedded;

import ged.ejb.core.model.AbstractEntity;
import ged.ejb.core.model.Ownerable;
import ged.ejb.job.candidature.JobCandidature;
import ged.ejb.user.User;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "jobCandidatureId", "datePlanned" }) })
public class Meeting extends AbstractEntity implements Ownerable {

	private static final long serialVersionUID = 3394583186288921090L;

	@OneToMany
	private List<User> attendees;

	@NotNull
	@Column(nullable = false)
	private LocalDateTime datePlanned;

	private String description;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "jobCandidatureId", nullable = false)
	private JobCandidature jobCandidature;

	private MeetingType meetingType;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "ownerId", nullable = false)
	@IndexedEmbedded
	private User owner;

	private String result;

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
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Meeting other = (Meeting) obj;
		return Objects.equals(this.datePlanned, other.datePlanned)
				&& Objects.equals(this.description, other.description)
				&& Objects.equals(this.jobCandidature, other.jobCandidature)
				&& Objects.equals(this.result, other.result);
	}

	public List<User> getAttendees() {
		return this.attendees;
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
		return meetingType;
	}

	@Override
	public User getOwner() {
		return owner;
	}

	public String getResult() {
		return this.result;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.datePlanned, this.description, this.jobCandidature, this.result);
	}

	public void setAttendees(final List<User> attendees) {
		this.attendees = attendees;
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

	public void setJobMeetingType(MeetingType meetingType) {
		this.meetingType = meetingType;
	}

	@Override
	public void setOwner(User owner) {
		this.owner = owner;
	}

	public void setResult(final String result) {
		this.result = result;
	}
}