package es.nivel36.laie.web.view.job;

import java.util.Objects;

import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.job.offer.JobOfferService;
import es.nivel36.laie.web.core.AbstractConverter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

@FacesConverter(managed = true, forClass = JobOffer.class)
public class JobOfferConverter extends AbstractConverter<JobOffer> {

	private @Inject JobOfferService jobOfferService;

	public void setJobOfferService(final JobOfferService jobOfferService) {
		Objects.requireNonNull(jobOfferService);
		this.jobOfferService = jobOfferService;
	}

	@Override
	protected JobOffer getAsObject(Long id) {
		return jobOfferService.findJobOfferById(id);
	}
}
