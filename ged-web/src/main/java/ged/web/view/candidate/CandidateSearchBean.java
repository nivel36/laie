package ged.web.view.candidate;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;

@Named
@ViewScoped
public class CandidateSearchBean extends AbstractCandidateSearchBean {

	private static final transient Logger logger = Logger.getLogger(CandidateSearchBean.class.getName());

	private static final long serialVersionUID = 2434819723782902618L;

	@Inject
	public CandidateSearchBean(final CandidateService candidateService) {
		super(candidateService);
	}

	@Override
	public String edit(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		logger.log(Level.FINE, "Editing a candidate");
		this.flash.put("candidate", candidate);
		return "candidateEdit?faces-redirect=true";
	}

	@Override
	public String newCandidate() {
		logger.log(Level.FINE, "New candidate");
		return "candidateEdit?faces-redirect=true";
	}

	@Override
	public void remove(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		logger.log(Level.FINE, "Deleting candidate");
		candidate.setUser(this.sessionBean.getUser());
		this.candidateService.delete(candidate);
		search();
	}
}