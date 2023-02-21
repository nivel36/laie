package es.nivel36.laie.web.view.candidate;

import java.util.Objects;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.candidate.CandidateService;
import es.nivel36.laie.web.core.AbstractConverter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

@FacesConverter(managed = true, forClass = Candidate.class)
public class CandidateConverter extends AbstractConverter<Candidate> {

	private @Inject CandidateService candidateService;

	@Override
	protected Candidate getAsObject(Long id) {
		return candidateService.findCandidateById(id);
	}

	public void setCandidateService(final CandidateService candidateService) {
		Objects.requireNonNull(candidateService);
		this.candidateService = candidateService;
	}
}