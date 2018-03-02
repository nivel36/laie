package ged.web.view.candidate;

import java.util.List;
import java.util.Objects;

import ged.ejb.candidate.Candidate;
import ged.web.core.ActionCallback;
import ged.web.view.job.JobOfferViewBean;

public final class SelectCandidatesAction implements ActionCallback<List<Candidate>> {

	private final JobOfferViewBean parent;

	public SelectCandidatesAction(final JobOfferViewBean parent) {
		super();
		Objects.requireNonNull(parent);
		this.parent = parent;
	}

	@Override
	public void doAction(final List<Candidate> o) {
		getParent().onCandidatesSelect(o);
	}

	public JobOfferViewBean getParent() {
		return this.parent;
	}
}
