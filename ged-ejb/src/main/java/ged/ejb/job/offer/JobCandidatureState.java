package ged.ejb.job.offer;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import ged.ejb.core.maintenance.AbstractEnumEntity;

@Entity
public class JobCandidatureState extends AbstractEnumEntity {

	private static final long serialVersionUID = -4388524806296516306L;

	@OneToMany
	private List<JobCandidatureState> nextStates;

	@OneToMany
	private List<JobCandidatureState> previousStates;

	
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		return super.equals(obj);
	}

	public List<JobCandidatureState> getNextStates() {
		return this.nextStates;
	}

	public List<JobCandidatureState> getPreviousStates() {
		return this.previousStates;
	}

	
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	public void setNextStates(final List<JobCandidatureState> nextStates) {
		this.nextStates = nextStates;
	}

	public void setPreviousStates(final List<JobCandidatureState> previousStates) {
		this.previousStates = previousStates;
	}
}