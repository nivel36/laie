package ged.web.view.job;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.user.User;

@Named
@ViewScoped
public class EditJobBean extends AbstractJobBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = -4373329969104383876L;

	@PostConstruct
	public void init() {
		logger.debug("Edit job offer {} init", this.jobOffer);
		this.recruiters = new ArrayList<>();
		for (final User recruiter : this.jobOffer.getRecruiters()) {
			this.recruiters.add(recruiter.getFullName());
		}
	}

	public String save() {
		this.jobOffer = this.jobOfferService.save(this.jobOffer);
		return jobUrl();
	}
}