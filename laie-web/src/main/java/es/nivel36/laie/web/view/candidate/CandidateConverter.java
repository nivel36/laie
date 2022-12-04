package es.nivel36.laie.web.view.candidate;

import java.util.Objects;

import javax.faces.convert.FacesConverter;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.candidate.CandidateService;
import es.nivel36.laie.web.core.AbstractConverter;
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