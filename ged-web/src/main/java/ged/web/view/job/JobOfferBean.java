package ged.web.view.job;

import java.lang.invoke.MethodHandles;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.web.core.PageNotFoundException;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class JobOfferBean extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = -1200840678252895578L;

	private JobOffer jobOffer;

	private Long jobOfferId;

	@Inject
	private transient JobOfferService jobService;

	public JobOffer getJobOffer() {
		return this.jobOffer;
	}

	public Long getJobOfferId() {
		return this.jobOfferId;
	}

	/**
	 * Not using @PostConstruct because the view is a GET based form.
	 */
	public void init() {
		logger.trace("JobOfferBean Init");
		if (this.jobOfferId == null) {
			throw new PageNotFoundException("JobOfferId is null");
		}
		this.jobOffer = this.jobService.find(this.jobOfferId);
		if (this.jobOffer == null) {
			throw new PageNotFoundException("Bad jobOfferId");
		}
	}

	// boolean -> is[name]
	public boolean isUserHasPermissionToEditJobOffer() {
		return this.userHasPermissionToEdit(this.jobOffer);
	}

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	public void setJobOfferId(final Long jobOfferId) {
		this.jobOfferId = jobOfferId;
	}

	public void setJobService(final JobOfferService jobService) {
		this.jobService = jobService;
	}
}