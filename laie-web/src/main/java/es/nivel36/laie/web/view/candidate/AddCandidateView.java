package es.nivel36.laie.web.view.candidate;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.candidate.Candidate;

@Named
@ViewScoped
public class AddCandidateView extends AbstractCandidateView {

	private static final long serialVersionUID = -3346845253673407579L;

	private static final Logger logger = LoggerFactory.getLogger(AddCandidateView.class);

	@PostConstruct
	public void init() {
		logger.trace("New candidate init");
		this.candidate = new Candidate();
		this.setTags(new ArrayList<>());
	}

	public String save() {
		logger.debug("Create new candidate action performed");
		this.candidate.setTags(this.getTags());
		this.candidate.setOwner(sessionUser.get());
		this.candidateService.addCandidate(candidate);
		return this.candidateUrl();
	}
}
