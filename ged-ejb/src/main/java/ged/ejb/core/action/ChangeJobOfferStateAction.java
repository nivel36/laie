package ged.ejb.core.action;

import ged.ejb.core.model.Ownerable;
import ged.ejb.job.offer.JobOffer;

public class ChangeJobOfferStateAction extends AbstractAction {
	
	private static final long serialVersionUID = 6050457663764873515L;
	
	private String fromState;

	private JobOffer jobOffer;

	private String toState;

	public String getFromState() {
		return fromState;
	}

	public JobOffer getJobOffer() {
		return jobOffer;
	}

	@Override
	Ownerable getObject() {
		return jobOffer;
	}

	public String getToState() {
		return toState;
	}

	public void setFromState(String fromState) {
		this.fromState = fromState;
	}
	
	public void setJobOffer(JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	public void setToState(String toState) {
		this.toState = toState;
	}
}
