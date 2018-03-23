package ged.web.view.candidate;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.web.core.view.AbstractBean;

@ViewScoped
@Named
public class CandidatePanelBean extends AbstractBean implements CandidateSelecteable {

	private static final long serialVersionUID = 3685531862855960321L;

	private List<Candidate> candidates;

	@Inject
	private CandidateService candidateService;

	@Inject
	@Param
	private Long jobOfferId;

	private final SelectCandidatesAction selectCandidateCallback = new SelectCandidatesAction(this);

	public List<Candidate> getCandidates() {
		return this.candidates;
	}

	public Long getJobOfferId() {
		return this.jobOfferId;
	}

	public SelectCandidatesAction getSelectCandidateCallback() {
		return this.selectCandidateCallback;
	}

	@PostConstruct
	public void init() {
		if (this.jobOfferId != null) {
			this.candidates = this.candidateService.findByJobOfferId(this.jobOfferId);
		}
	}

	@Override
	public void onCandidatesSelect(final List<Candidate> selectedCandidates) {
		this.candidates.addAll(selectedCandidates);
	}

	public void setCandidateService(final CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public void setJobOfferId(final Long jobOfferId) {
		this.jobOfferId = jobOfferId;
	}
}