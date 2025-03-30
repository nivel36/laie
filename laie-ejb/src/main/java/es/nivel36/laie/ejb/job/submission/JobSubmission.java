package es.nivel36.laie.ejb.job.submission;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.core.model.AbstractEntity;
import es.nivel36.laie.ejb.job.meeting.Meeting;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "JOB_SUBMISSION", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "JOB_OFFER_ID", "CANDIDATE_ID" }) })
public class JobSubmission extends AbstractEntity {

	private static final long serialVersionUID = 7312289648009726566L;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "JOB_SUBMISSION_STATE_ID", nullable = false)
	private JobSubmissionState state;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "CANDIDATE_ID", nullable = false)
	private Candidate candidate;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "JOB_OFFER_ID", nullable = false)
	private JobOffer jobOffer;

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "jobSubmission", orphanRemoval = true)
	private Set<JobSubmissionEvent> jobSubmissionEvents = new HashSet<>();

	@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
	private Set<Meeting> meetings = new HashSet<>();

	public JobSubmission() {
	}

	public JobSubmission(final Candidate candidate, final JobOffer jobOffer, final JobSubmissionState state) {
		this.candidate = Objects.requireNonNull(candidate);
		this.jobOffer = Objects.requireNonNull(jobOffer);
		this.state = Objects.requireNonNull(state);
	}

	public void addJobSubmissionEvent(final JobSubmissionEvent jobSubmissionEvent) {
		Objects.requireNonNull(jobSubmissionEvent);
		this.jobSubmissionEvents.add(jobSubmissionEvent);
	}

	public Candidate getCandidate() {
		return this.candidate;
	}

	public Set<JobSubmissionEvent> getJobSubmissionEvents() {
		return jobSubmissionEvents;
	}

	public Set<Meeting> getJobMeetings() {
		return this.meetings;
	}

	public JobOffer getJobOffer() {
		return this.jobOffer;
	}

	public Set<Meeting> getMeetings() {
		return meetings;
	}

	public JobSubmissionState getState() {
		return this.state;
	}

	public boolean hasState(final JobSubmissionState state) {
		Objects.requireNonNull(state);
		return state.equals(this.state);
	}

	public boolean isApproved() {
		if (this.state == null) {
			return false;
		}
		return this.state.isApproved();
	}

	public void setCandidate(final Candidate candidate) {
		this.candidate = candidate;
	}

	public void setJobSubmissionEvents(final Set<JobSubmissionEvent> jobSubmissionEvents) {
		this.jobSubmissionEvents = jobSubmissionEvents;
	}

	public void setJobMeetings(final Set<Meeting> meetings) {
		this.meetings = meetings;
	}

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	public void setMeetings(final Set<Meeting> meetings) {
		this.meetings = meetings;
	}

	public void setState(final JobSubmissionState state) {
		this.state = state;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (this.getClass() != obj.getClass())) {
			return false;
		}
		final JobSubmission other = (JobSubmission) obj;
		return Objects.equals(this.candidate, other.candidate) && Objects.equals(this.jobOffer, other.jobOffer);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.candidate, this.jobOffer);
	}

	@Override
	public String toString() {
		return this.jobOffer + " - " + this.candidate.getFullName();
	}
}
