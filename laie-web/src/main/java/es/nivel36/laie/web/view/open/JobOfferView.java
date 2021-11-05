package es.nivel36.laie.web.view.open;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.job.offer.JobOfferDto;
import es.nivel36.laie.ejb.job.offer.JobOfferService;
import es.nivel36.laie.web.core.IllegalPageStateException;
import es.nivel36.laie.web.core.view.AbstractView;

@Named
@RequestScoped
public class JobOfferView extends AbstractView {

	private static final long serialVersionUID = -5792496398007025173L;

	private static final Logger logger = LoggerFactory.getLogger(JobOfferView.class);

	private JobOfferDto jobOffer;
	
	private transient JobOfferService jobOfferService;

	@PostConstruct
	public void init() {
		String uid = this.getValueFromGetParameters("uid");
		if (uid == null) {
			throw new IllegalPageStateException("Uid not found");
		}
		this.jobOffer = jobOfferService.findJobOfferByUid(uid);
		if (this.jobOffer == null) {
			throw new IllegalPageStateException("Job offer not found");
		}
		if (!this.jobOffer.isPublished()) {
			throw new IllegalPageStateException("Trying to access to a non published job offer");
		}
		logger.trace("JobOffer {} init", this.jobOffer);
	}

	public JobOfferDto getJobOffer() {
		return this.jobOffer;
	}
}
