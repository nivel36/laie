package ged.web.view.job;

import java.lang.invoke.MethodHandles;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.job.offer.JobOffer;
import ged.web.core.IllegalPageStateException;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class PreviewJobView extends AbstractView {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1L;

	@Inject
	@Param(name = "id", required = true)
	private JobOffer jobOffer;

	public JobOffer getJobOffer() {
		return jobOffer;
	}
	
	@PostConstruct
	public void init() {
		if (this.jobOffer == null) {
			throw new IllegalPageStateException();
		}
		logger.trace("JobOffer {} init", this.jobOffer);
	}

	public void setJobOffer(JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}
}
