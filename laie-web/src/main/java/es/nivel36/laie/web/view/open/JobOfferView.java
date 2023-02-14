package es.nivel36.laie.web.view.open;

import org.omnifaces.cdi.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.web.core.IllegalPageStateException;
import es.nivel36.laie.web.core.view.AbstractView;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class JobOfferView extends AbstractView {

	private static final long serialVersionUID = -5792496398007025173L;

	private static final Logger logger = LoggerFactory.getLogger(JobOfferView.class);

	private @Param JobOffer jobOffer;

	@PostConstruct
	public void init() {
		if (this.jobOffer == null) {
			throw new IllegalPageStateException("Job offer not found");
		}
		if (!this.jobOffer.isPublished()) {
			throw new IllegalPageStateException("Trying to access to a non published job offer");
		}
		logger.trace("JobOffer {} init", this.jobOffer);
	}

	public JobOffer getJobOffer() {
		return this.jobOffer;
	}
}
