package es.nivel36.laie.ejb.job.candidature;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.core.model.AbstractObfuscableEntity;
import es.nivel36.laie.ejb.event.JobCandidatureEvent;
import es.nivel36.laie.ejb.job.meeting.Meeting;
import es.nivel36.laie.ejb.job.offer.JobOffer;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "jobOfferId", "candidateId" }) })
public class JobCandidature extends AbstractObfuscableEntity {

	private static final long serialVersionUID = 7312289648009726566L;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "candidateId", nullable = false)
	private Candidate candidate;

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "jobCandidature", orphanRemoval = true)
	private Set<JobCandidatureEvent> jobCandidatureEvents = new HashSet<>();

	@NotNull
	@ManyToOne
	@JoinColumn(name = "jobOfferId", nullable = false)
	private JobOffer jobOffer;

	@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JoinColumn(name = "jobCandidatureId")
	private Set<Meeting> meetings;

	@ManyToOne
	@JoinColumn(name = "jobCandidatureStateId")
	private JobCandidatureState state;

	public JobCandidature() {
	}

	public JobCandidature(final Candidate candidate, final JobOffer jobOffer) {
		this.candidate = candidate;
		this.jobOffer = jobOffer;
	}

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
		final JobCandidature other = (JobCandidature) obj;
		return Objects.equals(this.candidate, other.candidate) && Objects.equals(this.jobOffer, other.jobOffer);
	}

	public Candidate getCandidate() {
		return this.candidate;
	}

	public Set<Meeting> getJobMeetings() {
		return this.meetings;
	}

	public JobOffer getJobOffer() {
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

	public void setCandidate(final Candidate candidate) {
		this.candidate = candidate;
	}

	public void setJobMeetings(final Set<Meeting> meetings) {
		this.meetings = meetings;
	}

	public void setJobOffer(final JobOffer jobOffer) {
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
