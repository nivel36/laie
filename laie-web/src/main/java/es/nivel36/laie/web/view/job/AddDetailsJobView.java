package es.nivel36.laie.web.view.job;

import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.web.core.IllegalPageStateException;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named
@ViewScoped
public class AddDetailsJobView extends AbstractJobView {

	private static final long serialVersionUID = -7323264602495428654L;
	
	private static final Logger logger = LoggerFactory.getLogger(AddDetailsJobView.class);

	@PostConstruct
	public void init() {
		if (this.jobOffer == null) {
			logger.error("Trying to edit a job offer but job is null");
			throw new IllegalPageStateException();
		}
		logger.trace("Edit job offer {} init", this.jobOffer);
	}

	public void save() {
		logger.debug("Save job offer action performed");
		this.jobOfferService.updateJobOffer(jobOffer);
		Faces.redirect(ViewJobView.getUrl(this.jobOffer.getId()));
	}
}