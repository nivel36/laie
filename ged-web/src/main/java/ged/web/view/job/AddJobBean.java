package ged.web.view.job;

import java.lang.invoke.MethodHandles;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.job.offer.JobOffer;
import ged.ejb.user.User;
import ged.web.core.util.Navigate;
import ged.web.core.util.PageEnum;

@Named
@ViewScoped
public class AddJobBean extends AbstractJobBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = -4373329969104383876L;

	@PostConstruct
	public void init() {
		logger.debug("New job offer init");
		if (this.flashContainsKey("saveState")) {
			this.jobOffer = this.getValueFromFlash("state");
		} else {
			this.jobOffer = new JobOffer();
		}
		final User user = this.setSessionUserAsOwner();
		this.setSubordinateUsersAsRecruiters(user);
		this.setClientFromFlash();
	}

	public String save() {
		this.jobOffer = this.jobOfferService.create(this.jobOffer);
		return jobUrl();
	}

	public void searchClient() {
		putValueToFlash("state", this.jobOffer);
		putValueToFlash("url", PageEnum.JOB_ADD.getRedirectUrl());
		Navigate.to(PageEnum.CLIENT_SELECT).doPost();
	}
}