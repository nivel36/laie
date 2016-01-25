package ged.web.view;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.service.candidate.Candidate;
import ged.ejb.service.candidate.CandidateService;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class IndexBean extends AbstractBean {

	private static final long serialVersionUID = 3050470966176013477L;

	private List<Candidate> candidates;

	@Inject
	private CandidateService candidateService;

	public List<Candidate> getCandidates() {
		return candidates;
	}

	@PostConstruct
	public void init() {
		candidates = candidateService.getByProperties(Candidate.class, null, 6,
				0);
	}

	public void setCandidates(List<Candidate> candidates) {
		this.candidates = candidates;
	}
}
