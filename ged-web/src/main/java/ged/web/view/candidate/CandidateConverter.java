package ged.web.view.candidate;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;

@FacesConverter(managed = true, forClass = Candidate.class)
public class CandidateConverter implements Converter<Candidate> {

	@Inject
	private CandidateService candidateService;

	public void setCandidateService(final CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	@Override
	public Candidate getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if (value == null) {
			return null;
		}
		return this.candidateService.findByUid(value);
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Candidate value) {
		if (value == null) {
			return null;
		}
		return value.getUid();
	}
}