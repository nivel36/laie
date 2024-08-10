package es.nivel36.laie.ejb.job.offer;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import es.nivel36.laie.ejb.core.model.AbstractEntity;
import es.nivel36.laie.ejb.job.submission.JobSubmissionState;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "JOB_OFFER_PROCESS")
public class JobOfferProcess extends AbstractEntity {

	private static final long serialVersionUID = -8072053913508187851L;

	private String name;

	@OneToMany(mappedBy = "jobOfferProcess", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<JobSubmissionState> states = new HashSet<>();

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj) || (getClass() != obj.getClass())) {
			return false;
		}
		JobOfferProcess other = (JobOfferProcess) obj;
		return Objects.equals(name, other.name);
	}

	public String getName() {
		return name;
	}

	public Set<JobSubmissionState> getStates() {
		return states;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(name);
		return result;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStates(Set<JobSubmissionState> states) {
		this.states = states;
	}

	@Override
	public String toString() {
		return name;
	}
}
