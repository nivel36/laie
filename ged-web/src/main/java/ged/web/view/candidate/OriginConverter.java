package ged.web.view.candidate;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import ged.ejb.candidate.CandidateService;
import ged.ejb.candidate.Origin;

@FacesConverter(managed = true, forClass = Origin.class)
public class OriginConverter implements Converter<Origin> {

	@Inject
	private transient CandidateService candidateService;

	@Override
	public Origin getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if (value == null) {
			return null;
		}
		final List<Origin> listOfOrigins = this.candidateService.findAllOrigins();
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
