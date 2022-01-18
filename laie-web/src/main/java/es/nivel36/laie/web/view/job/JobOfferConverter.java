package es.nivel36.laie.web.view.job;

import java.util.Objects;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.job.offer.JobOfferDto;
import es.nivel36.laie.ejb.job.offer.JobOfferService;

@FacesConverter(managed = true, forClass = JobOffer.class)
public class JobOfferConverter implements Converter<JobOfferDto> {

	@Inject
	private JobOfferService jobOfferService;

	@Override
	public JobOfferDto getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if (value == null) {
			return null;
		}
		return this.jobOfferService.findJobOfferByUid(value);
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final JobOfferDto value) {
		if (value == null) {
			return null;
		}
		return value.getUid();
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		Objects.requireNonNull(jobOfferService);
		this.jobOfferService = jobOfferService;
	}
}
