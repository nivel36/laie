package es.nivel36.laie.web.view.job;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.web.core.IllegalPageStateException;

@Named
@ViewScoped
public class EditJobView extends AbstractJobView {

	private static final long serialVersionUID = 7356542779288827753L;

	private static final Logger logger = LoggerFactory.getLogger(EditJobView.class);

	public static String URL = "/job/edit.xhtml";

	@PostConstruct
	public void init() {
		final String uid = this.getValueFromGetParameters("jobOfferUid", true);
		this.jobOffer = this.jobOfferService.findJobOfferByUid(uid);
		this.checkNonNullJobOffer();
		logger.trace("Edit job offer {} init", this.jobOffer);
		this.fillRecruiters();
	}

	private void checkNonNullJobOffer() {
		if (this.jobOffer == null) {
			logger.error("Trying to edit a job offer but job is null");
			throw new IllegalPageStateException();
		}
	}

	private void fillRecruiters() {
		this.recruiters = new ArrayList<>(this.jobOffer.getRecruiters());
	}

	public void save() {
		logger.debug("Save job offer action performed");
		this.jobOfferService.updateJobOffer(jobOffer);
		this.navigateTo(ViewJobView.URL + "?job=" + this.jobOffer.getUid());
	}
}