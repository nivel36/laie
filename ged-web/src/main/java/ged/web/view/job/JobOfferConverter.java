package ged.web.view.job;

import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import ged.ejb.core.AbstractService;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.web.core.view.AbstractConverter;

@FacesConverter(managed = true, forClass = JobOffer.class)
public class JobOfferConverter extends AbstractConverter<JobOffer> {
	
	@Inject
	private JobOfferService jobOfferService;

	@Override
	protected AbstractService<JobOffer> getService() {
		return this.jobOfferService;
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}

}
