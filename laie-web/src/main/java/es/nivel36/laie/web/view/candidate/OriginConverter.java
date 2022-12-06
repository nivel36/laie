package es.nivel36.laie.web.view.candidate;

import java.util.List;

import es.nivel36.laie.ejb.candidate.CandidateService;
import es.nivel36.laie.ejb.candidate.Origin;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

@FacesConverter(managed = true, forClass = Origin.class)
public class OriginConverter implements Converter<Origin> {

	private @Inject CandidateService candidateService;

	@Override
	public Origin getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if (value == null) {
			return null;
		}
		final List<Origin> listOfOrigins = this.candidateService.findCandidateOrigins();
		for (final Origin origin : listOfOrigins) {
			if (origin.getCode().equals(value)) {
				return origin;
			}
		}
		return null;
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Origin value) {
		if (value == null) {
			return null;
		}
		return value.getCode();
	}
}
