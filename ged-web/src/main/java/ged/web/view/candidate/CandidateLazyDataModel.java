package ged.web.view.candidate;

import java.util.Objects;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.core.AbstractIndexedService;
import ged.web.core.view.AbstractLazyDataModel;

public class CandidateLazyDataModel extends AbstractLazyDataModel<Candidate> {

	private static final long serialVersionUID = 1L;

	private transient CandidateService candidateService;

	public CandidateLazyDataModel(final CandidateService candidateService) {
		Objects.requireNonNull(candidateService, "CandidateService can't be null");
		this.candidateService = candidateService;
	}

	@Override
	protected AbstractIndexedService<Candidate> getService() {
		return this.candidateService;
	}
}
