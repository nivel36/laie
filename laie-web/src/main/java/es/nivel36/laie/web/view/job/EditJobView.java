package es.nivel36.laie.web.view.job;

import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.web.core.IllegalPageStateException;

@Named
@ViewScoped
public class EditJobView extends AbstractJobView {

	private static final long serialVersionUID = 7356542779288827753L;

	private static final Logger logger = LoggerFactory.getLogger(EditJobView.class);

	@PostConstruct
	public void init() {
		if (this.jobOffer == null) {
			logger.error("Trying to edit a job offer but job is null");
			throw new IllegalPageStateException();
		}
		logger.trace("Edit job offer {} init", this.jobOffer);
		this.fillRecruiters();
	}

	private void fillRecruiters() {
		this.recruiters = this.jobOffer.getRecruiters().stream().map(User::getEmail).collect(Collectors.toList());
	}

	public void save() {
		logger.debug("Save job offer action performed");
		this.convertRecruiters();
		this.jobOfferService.updateJobOffer(jobOffer);
		Faces.redirect(ViewJobView.getUrl(this.jobOffer.getId()));
	}

	public void next() {
		this.convertRecruiters();
		this.jobOffer = this.jobOfferService.updateJobOffer(jobOffer);
		Faces.redirect("/job/editDetails.xhtml?jobOffer=" + this.jobOffer.getId());
	}
}