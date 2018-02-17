package ged.web.view.candidate;

import static ged.web.core.util.Navigate.to;
import static ged.web.core.util.Page.CANDIDATE;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;

@Named
@ViewScoped
public class CandidateListPanelBean implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 3521573145970724629L;

	@Inject
	protected transient CandidateService candidateService;

	private List<Candidate> lastAddedCandidates;

	private long numberOfCandidates;

	public List<Candidate> getLastAddedCandidates() {
		return this.lastAddedCandidates;
	}

	public long getNumberOfCandidates() {
		return this.numberOfCandidates;
	}

	@PostConstruct
	public void init() {
		this.numberOfCandidates = this.candidateService.findNumberOfCandidates();
		this.lastAddedCandidates = this.candidateService.findLastAddedCandidates(6);
	}

	public String newCandidate() {
		logger.debug("New candidate");
		return to(CANDIDATE).toUrl();
	}

	public void setCandidateService(final CandidateService candidateService) {
		this.candidateService = candidateService;
	}
}