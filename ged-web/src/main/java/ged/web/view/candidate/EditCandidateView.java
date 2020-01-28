package ged.web.view.candidate;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@ViewScoped
public class EditCandidateView extends AbstractCandidateView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1L;

	private void checkEditPermission() {
		if (!this.sessionUser.hasPermissionToEdit(this.candidate)) {
			logger.error("User {} hasn't got priviliges to edit candidate {}", this.sessionUser.get(), this.candidate);
			throw new SecurityException();
		}
	}

	@PostConstruct
	public void init() {
		this.checkEditPermission();
		logger.trace("Candidate {} edit init", this.candidate);
		this.initTags();
	}

	private void initTags() {
		if (this.candidate.getTags() == null) {
			this.tags = new ArrayList<>();
		} else {
			this.tags = new ArrayList<>(this.candidate.getTags());			
		}
	}

	public String save() {
		logger.debug("Save candidate action performed");
		this.candidate.setTags(this.getTags());
		this.candidate = this.candidateService.save(this.candidate);
		return this.candidateUrl();
	}
}