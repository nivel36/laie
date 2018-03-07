package ged.web.view.candidate;

import java.util.List;
import java.util.Objects;

import ged.ejb.candidate.Candidate;
import ged.web.core.ActionCallback;

public final class SelectCandidatesAction implements ActionCallback<List<Candidate>> {

	private final CandidateSelecteable parent;

	public SelectCandidatesAction(final CandidateSelecteable parent) {
		Objects.requireNonNull(parent);
		this.parent = parent;
	}

	@Override
	public void doAction(final List<Candidate> o) {
		this.parent.onCandidatesSelect(o);
	}
}
