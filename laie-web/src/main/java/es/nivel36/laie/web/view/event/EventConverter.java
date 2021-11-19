package es.nivel36.laie.web.view.event;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import es.nivel36.laie.ejb.event.JobCandidatureEventDto;
import es.nivel36.laie.ejb.event.JobCandidatureEventService;

@FacesConverter(managed = true, forClass = JobCandidatureEventDto.class)
public class EventConverter implements Converter<JobCandidatureEventDto>{

	private JobCandidatureEventService jobCandidatureEventService;

	@Override
	public JobCandidatureEventDto getAsObject(FacesContext context, UIComponent component, String value) {
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, JobCandidatureEventDto value) {
		return null;
	}

	
}