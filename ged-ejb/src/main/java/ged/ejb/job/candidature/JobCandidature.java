package ged.ejb.job.candidature;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.model.AbstractEntity;
import ged.ejb.job.meeting.Meeting;
import ged.ejb.job.offer.JobOffer;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "jobOfferId", "candidateId" }) })
public class JobCandidature extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "candidateId", nullable = false)
	private Candidate candidate;

	@ManyToOne
	@JoinColumn(name = "jobCandidatureStateId")
	private JobCandidatureState jobCandidatureState;

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "jobCandidature", orphanRemoval = true)
	private List<Meeting> meetings;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "jobOfferId", nullable = false)
	private JobOffer jobOffer;

	public JobCandidature() {
	}

	public JobCandidature(final Candidate candidate, final JobOffer jobOffer) {
		this.candidate = candidate;
		this.jobOffer = jobOffer;
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

	public JobCandidatureState getJobCandidatureState() {
		return this.jobCandidatureState;
	}

	public List<Meeting> getJobMeetings() {
		return this.meetings;
	}

	public JobOffer getJobOffer() {
		return this.jobOffer;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.candidate, this.jobOffer);
	}

	public void setCandidate(final Candidate candidate) {
		this.candidate = candidate;
	}

	public void setJobCandidatureState(final JobCandidatureState jobCandidatureState) {
		this.jobCandidatureState = jobCandidatureState;
	}

	public void setJobMeetings(final List<Meeting> meetings) {
		this.meetings = meetings;
	}

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	@Override
	public String toString() {
		return this.jobOffer + " - " + this.candidate.getFullName();
	}
}
