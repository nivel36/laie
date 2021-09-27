package es.nivel36.laie.web.view.candidate;

import java.util.Objects;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.candidate.CandidateService;
import es.nivel36.laie.ejb.core.AbstractIndexedService;
import es.nivel36.laie.web.core.view.AbstractLazyDataModel;

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
