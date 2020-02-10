package ged.ejb.job.candidature;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.model.AbstractEntity;
import ged.ejb.core.model.Obfuscable;
import ged.ejb.event.JobCandidatureEvent;
import ged.ejb.job.meeting.Meeting;
import ged.ejb.job.offer.JobOffer;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "jobOfferId", "candidateId" }) })
public class JobCandidature extends AbstractEntity implements Obfuscable {

	private static final long serialVersionUID = 1L;

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

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "jobCandidature", orphanRemoval = true)
	private Set<Meeting> meetings;

	@ManyToOne
	@JoinColumn(name = "jobCandidatureStateId")
	private JobCandidatureState state;

	@NotNull
	@Column(unique = true, nullable = false)
	private String uid;

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
	public String getUid() {
		return this.uid;
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
	public void setUid(final String uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		return this.jobOffer + " - " + this.candidate.getFullName();
	}
}
