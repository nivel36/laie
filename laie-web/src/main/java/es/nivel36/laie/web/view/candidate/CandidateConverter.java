package es.nivel36.laie.web.view.candidate;

import java.util.Objects;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import es.nivel36.laie.ejb.candidate.CandidateDto;
import es.nivel36.laie.ejb.candidate.CandidateService;

@FacesConverter(managed = true, forClass = CandidateDto.class)
public class CandidateConverter implements Converter<CandidateDto> {

	@Inject
	private CandidateService candidateService;

	@Override
	public CandidateDto getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if (value == null) {
			return null;
		}
		return this.candidateService.findCandidateByUid(value);
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final CandidateDto value) {
		if (value == null) {
			return null;
		}
		return value.getUid();
	}

	public void setCandidateService(final CandidateService candidateService) {
		Objects.requireNonNull(candidateService);
		this.candidateService = candidateService;
	}
}