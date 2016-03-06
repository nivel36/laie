package ged.web.view;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ged.ejb.candidate.Candidate;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class IndexBean extends AbstractBean {

	private static final long serialVersionUID = 3050470966176013477L;

	private List<Candidate> candidates;

	public List<Candidate> getCandidates() {
		return this.candidates;
	}

	@PostConstruct
	public void init() {
	}

	public void setCandidates(final List<Candidate> candidates) {
		this.candidates = candidates;
	}
}
