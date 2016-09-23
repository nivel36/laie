package ged.ejb.job;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.model.AbstractAuditedEntity;
import ged.ejb.job.meeting.JobMeeting;
import ged.ejb.job.offer.JobOffer;

@Entity
public class JobCandidature extends AbstractAuditedEntity {

	private static final long serialVersionUID = 4596378123715515824L;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "jobOfferId", nullable = false)
	private JobOffer jobOffer;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "candidateId", nullable = false)
	private Candidate candidate;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "jobCandidature", orphanRemoval = true)
	private List<JobMeeting> jobMeetings;

	public Candidate getCandidate() {
		return candidate;
	}

	public JobOffer getJobOffer() {
		return jobOffer;
	}

	public List<JobMeeting> getJobMeetings() {
		return jobMeetings;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public void setJobOffer(JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	public void setJobMeetings(List<JobMeeting> jobMeetings) {
		this.jobMeetings = jobMeetings;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((candidate == null) ? 0 : candidate.hashCode());
		result = prime * result + ((jobOffer == null) ? 0 : jobOffer.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JobCandidature other = (JobCandidature) obj;
		if (candidate == null) {
			if (other.candidate != null)
				return false;
		} else if (!candidate.equals(other.candidate))
			return false;
		if (jobOffer == null) {
			if (other.jobOffer != null)
				return false;
		} else if (!jobOffer.equals(other.jobOffer))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JobCandidature [jobOffer=" + jobOffer + ", candidate=" + candidate + ", jobMeetings=" + jobMeetings
				+ "]";
	}
}
