package ged.web.view.candidate;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.web.core.IllegalPageStateException;

@Named
@ViewScoped
public class EditCandidateView extends AbstractCandidateView {

	private static final String CANDIDATE_KEY = "candidate";

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = -7336942836739051499L;

	private void checkEditPermission() {
		if (!this.sessionUser.hasPermissionToEdit(this.candidate)) {
			logger.error("User {} hasn't got priviliges to edit candidate {}", this.sessionUser.get(), this.candidate);
			throw new SecurityException();
		}
	}

	private void checkNonNullCandidate() {
		if (this.candidate == null) {
			logger.error("Trying to edit a candidate but candidate is null");
			throw new IllegalPageStateException();
		}
	}

	@PostConstruct
	public void init() {
		this.candidate = this.getValueFromFlash(CANDIDATE_KEY);
		this.checkNonNullCandidate();
		this.checkEditPermission();
		logger.trace("Candidate {} edit init", this.candidate);
		this.putValueToFlash(CANDIDATE_KEY, this.candidate); // prevent errors if f5/reload is pressed
		this.initTags();
	}

	private void initTags() {
		this.tags = new ArrayList<>(this.candidate.getTags());
	}

	public String save() {
		logger.debug("Save candidate action performed");
		this.candidate.setTags(this.getTags());
		this.candidate = this.candidateService.save(this.candidate);
		return this.candidateUrl();
	}

}