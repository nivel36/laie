package ged.ejb.job;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.model.AbstractAuditedEntity;
import ged.ejb.job.meeting.JobMeeting;
import ged.ejb.job.offer.JobOffer;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "jobOfferId", "candidateId" }) })
public class JobCandidature extends AbstractAuditedEntity {

	private static final long serialVersionUID = 4596378123715515824L;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "candidateId", nullable = false)
	private Candidate candidate;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "jobCandidature", orphanRemoval = true)
	private List<JobMeeting> jobMeetings;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "jobOfferId", nullable = false)
	private JobOffer jobOffer;

	public JobCandidature() {
	}

	public JobCandidature(final JobOffer jobOffer, final Candidate candidate) {
		this.jobOffer = jobOffer;
		this.candidate = candidate;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final JobCandidature other = (JobCandidature) obj;
		if (this.candidate == null) {
			if (other.candidate != null) {
				return false;
			}
		} else if (!this.candidate.equals(other.candidate)) {
			return false;
		}
		if (this.jobOffer == null) {
			if (other.jobOffer != null) {
				return false;
			}
		} else if (!this.jobOffer.equals(other.jobOffer)) {
			return false;
		}
		return true;
	}

	public Candidate getCandidate() {
		return this.candidate;
	}

	public List<JobMeeting> getJobMeetings() {
		return this.jobMeetings;
	}

	public JobOffer getJobOffer() {
		return this.jobOffer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.candidate == null ? 0 : this.candidate.hashCode());
		result = prime * result + (this.jobOffer == null ? 0 : this.jobOffer.hashCode());
		return result;
	}

	public void setCandidate(final Candidate candidate) {
		this.candidate = candidate;
	}

	public void setJobMeetings(final List<JobMeeting> jobMeetings) {
		this.jobMeetings = jobMeetings;
	}

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	@Override
	public String toString() {
		return "JobCandidature [jobOffer=" + this.jobOffer + ", candidate=" + this.candidate + ", jobMeetings="
				+ this.jobMeetings + "]";
	}
}
