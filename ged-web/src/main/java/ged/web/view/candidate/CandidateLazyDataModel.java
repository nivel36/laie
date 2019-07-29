package ged.web.view.candidate;

import java.util.Objects;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.core.AbstractService;
import ged.web.core.view.AbstractLazyDataModel;

public class CandidateLazyDataModel extends AbstractLazyDataModel<Candidate> {

	private static final long serialVersionUID = -6450532500586197930L;

	private transient CandidateService candidateService;

	public CandidateLazyDataModel(final CandidateService candidateService) {
		Objects.requireNonNull(candidateService, "CandidateService can't be null");
		this.candidateService = candidateService;
	}

	@Override
	protected AbstractService<Candidate> getService() {
		return candidateService;
	}
}
