package ged.web.view.candidate;

import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.core.Service;
import ged.web.core.view.AbstractConverter;

@FacesConverter(managed = true, forClass = Candidate.class)
public class CandidateConverter extends AbstractConverter<Candidate> {

	@Inject
	private CandidateService candidateService;

	@Override
	protected Service<Candidate> getService() {
		return this.candidateService;
	}

	public void setCandidateService(final CandidateService candidateService) {
		this.candidateService = candidateService;
	}
}