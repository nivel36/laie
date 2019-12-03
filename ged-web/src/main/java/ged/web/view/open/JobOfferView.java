package ged.web.view.open;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.job.offer.JobOffer;
import ged.web.core.IllegalPageStateException;

@Named
@RequestScoped
public class JobOfferView implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1L;

	@Inject
	@Param(name = "id", required = true)
	private JobOffer jobOffer;

	public JobOffer getJobOffer() {
		return this.jobOffer;
	}

	@PostConstruct
	public void init() {
		if (this.jobOffer == null) {
			throw new IllegalPageStateException();
		}
		logger.trace("JobOffer {} init", this.jobOffer);
	}

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}
}
