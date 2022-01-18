package es.nivel36.laie.ejb.job.candidature;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import es.nivel36.laie.ejb.candidate.SimpleCandidateDto;
import es.nivel36.laie.ejb.event.JobCandidatureEvent;
import es.nivel36.laie.ejb.job.meeting.MeetingDto;
import es.nivel36.laie.ejb.job.offer.JobOfferDto;

public class JobCandidatureDto implements Serializable {

	private static final long serialVersionUID = 94627874921747868L;

	private SimpleCandidateDto candidate;

	private Set<JobCandidatureEvent> jobCandidatureEvents = new HashSet<>();

	private JobOfferDto jobOffer;

	private Set<MeetingDto> meetings;

	private JobCandidatureState state;

	public void addJobCandidatureEvent(final JobCandidatureEvent jobCandidatureEvent) {
		Objects.requireNonNull(jobCandidatureEvent);
		this.jobCandidatureEvents.add(jobCandidatureEvent);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final JobCandidatureDto other = (JobCandidatureDto) obj;
		return Objects.equals(this.candidate, other.candidate) && Objects.equals(this.jobOffer, other.jobOffer);
	}

	public SimpleCandidateDto getCandidate() {
		return this.candidate;
	}

	public Set<MeetingDto> getJobMeetings() {
		return this.meetings;
	}

	public JobOfferDto getJobOffer() {
		return this.jobOffer;
	}

	public JobCandidatureState getState() {
		return this.state;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.candidate, this.jobOffer);
	}

	public boolean hasState(final JobCandidatureState state) {
		if (state == null) {
			return this.state == null;
		} else {
			return this.state.equals(state);
		}
	}

	public boolean isApproved() {
		if (this.state == null) {
			return false;
		}
		return this.state.isApproved();
	}

	public void setCandidate(final SimpleCandidateDto candidate) {
		this.candidate = candidate;
	}

	public void setJobMeetings(final Set<MeetingDto> meetings) {
		this.meetings = meetings;
	}

	public void setJobOffer(final JobOfferDto jobOffer) {
		this.jobOffer = jobOffer;
	}

	public void setState(final JobCandidatureState state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return this.jobOffer + " - " + this.candidate.getFullName();
	}
}
