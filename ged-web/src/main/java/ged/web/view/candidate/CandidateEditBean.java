package ged.web.view.candidate;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.service.candidate.Address;
import ged.ejb.service.candidate.Candidate;
import ged.ejb.service.candidate.CandidateService;
import ged.ejb.service.curriculum.Curriculum;
import ged.ejb.service.curriculum.CurriculumService;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class CandidateEditBean extends AbstractBean {

	private static final long serialVersionUID = -4334616177754425866L;

	private Candidate candidate;

	@Inject
	private CurriculumService curriculumService;

	@Inject
	private CandidateService candidateService;

	// ////////////////////////////////////////////////////////////////////////
	// INIT
	// ////////////////////////////////////////////////////////////////////////

	@PostConstruct
	private void init() {
		if (flash.containsKey("candidate")) {
			candidate = (Candidate) flash.get("candidate");
		} else {
			candidate = new Candidate();
		}
		if (candidate.getAddress() == null) {
			candidate.setAddress(new Address());
		}
		flash.put("candidate", candidate);
	}

	// ////////////////////////////////////////////////////////////////////////
	// SET AND GETS
	// ////////////////////////////////////////////////////////////////////////

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	// ////////////////////////////////////////////////////////////////////////
	// ACTIONS
	// ////////////////////////////////////////////////////////////////////////

	public String editCurriculum() {
		candidate = candidateService.insertOrUpdate(candidate);
		flash.put("curriculum", getCurriculum());
		return "curriculumEdit?faces-redirect=true";
	}

	private Curriculum getCurriculum() {
		Curriculum curriculum = curriculumService.getByCandidate(candidate);
		if (curriculum == null) {
			curriculum = new Curriculum();
			curriculum.setCandidate(candidate);
		}
		return curriculum;
	}

	public String cancel() {
		return "candidateSearch?faces-redirect=true";
	}

	public void save() {
		candidateService.insertOrUpdate(candidate);
	}
}
