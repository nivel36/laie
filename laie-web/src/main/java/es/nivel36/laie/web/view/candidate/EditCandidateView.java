package es.nivel36.laie.web.view.candidate;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.web.core.IllegalPageStateException;

@Named
@ViewScoped
public class EditCandidateView extends AbstractCandidateView {

	private static final long serialVersionUID = -5736997599473134307L;

	private static final Logger logger = LoggerFactory.getLogger(EditCandidateView.class);

	@PostConstruct
	public void init() {
		if (this.candidate == null) {
			throw new IllegalPageStateException();
		}
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
		this.candidateService.updateCandidate(candidate);
		return this.candidateUrl();
	}
}