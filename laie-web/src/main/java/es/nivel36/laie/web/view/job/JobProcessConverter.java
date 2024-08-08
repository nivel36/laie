package es.nivel36.laie.web.view.job;

import java.util.Objects;

import es.nivel36.laie.ejb.job.offer.JobOfferProcess;
import es.nivel36.laie.ejb.job.offer.JobOfferService;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

@FacesConverter(managed = true, forClass = JobOfferProcess.class)
public class JobProcessConverter implements Converter<JobOfferProcess> {

	private @Inject JobOfferService jobOfferService;	

	@Override
	public JobOfferProcess getAsObject(FacesContext context, UIComponent component, String value) {
		Objects.requireNonNull(value);
		return jobOfferService.findJobOfferProcessByName(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, JobOfferProcess value) {
		Objects.requireNonNull(value);
		return value.getName();
	}
	
	public void setJobOfferService(final JobOfferService jobOfferService) {
		Objects.requireNonNull(jobOfferService);
		this.jobOfferService = jobOfferService;
	}
}
