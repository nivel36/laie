package es.nivel36.laie.web.view.candidate;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import es.nivel36.laie.ejb.candidate.CandidateService;
import es.nivel36.laie.ejb.candidate.Origin;

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
