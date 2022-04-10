package es.nivel36.laie.web.view.job;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.job.offer.JobOfferService;
import es.nivel36.laie.web.core.IllegalPageStateException;
import es.nivel36.laie.web.core.view.AbstractView;

@Named
@ViewScoped
public class PreviewJobView extends AbstractView {

	private static final long serialVersionUID = 2762548551365739499L;

	private static final Logger logger = LoggerFactory.getLogger(PreviewJobView.class);

	private JobOffer jobOffer;
	
	@Inject
	private transient JobOfferService jobOfferService;

	@PostConstruct
	public void init() {
		final String Id = this.getValueFromGetParameters("job", true);
		this.jobOffer = this.jobOfferService.findJobOfferById(Id);
		if (this.jobOffer == null) {
			throw new IllegalPageStateException();
		}
		logger.trace("JobOffer {} init", this.jobOffer);
	}
	
	public JobOffer getJobOffer() {
		return this.jobOffer;
	}

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}
	
	public void setJobOfferService(final JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}
}
