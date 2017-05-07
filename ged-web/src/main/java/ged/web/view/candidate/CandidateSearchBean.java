package ged.web.view.candidate;

import java.util.Objects;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;

@Named
@ViewScoped
public class CandidateSearchBean extends AbstractCandidateSearchBean {

	private static final transient Logger logger = LoggerFactory.getLogger(CandidateSearchBean.class.getName());

	private static final long serialVersionUID = 2434819723782902618L;

	@Inject
	public CandidateSearchBean(final CandidateService candidateService) {
		super(candidateService);
	}

	@Override
	public String edit(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		logger.debug("Editing a candidate");
		this.flash.put("candidate", candidate);
		return "candidateEdit?faces-redirect=true";
	}

	@Override
	public String newCandidate() {
		logger.debug("New candidate");
		return "candidateEdit?faces-redirect=true";
	}

	@Override
	public void remove(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		logger.debug("Deleting candidate");
		candidate.setUser(this.sessionBean.getUser());
		this.candidateService.delete(candidate);
		search();
	}

	public String view() {
		return "candidateEdit?faces-redirect=true&includeViewParams=true";

	}
}