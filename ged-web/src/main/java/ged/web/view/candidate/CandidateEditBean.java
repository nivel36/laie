package ged.web.view.candidate;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.candidate.Address;
import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.curriculum.Curriculum;
import ged.ejb.curriculum.CurriculumService;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class CandidateEditBean extends AbstractBean {

	private static final long serialVersionUID = -4334616177754425866L;

	private Candidate candidate;

	@Inject
	private CandidateService candidateService;

	@Inject
	private CurriculumService curriculumService;

	public String cancel() {
		return "candidateSearch?faces-redirect=true";
	}

	public String editCurriculum() {
		insertOrUpdate(this.candidate);
		this.flash.put("curriculum", getCurriculum());
		return "curriculumEdit?faces-redirect=true";
	}

	public Candidate getCandidate() {
		return this.candidate;
	}

	private Curriculum getCurriculum() {
		Curriculum curriculum = this.curriculumService.findByCandidateId(this.candidate.getId());
		if (curriculum == null) {
			curriculum = new Curriculum();
			curriculum.setCandidate(this.candidate);
		}
		return curriculum;
	}

	@PostConstruct
	private void init() {
		if (this.flash.containsKey("candidate")) {
			this.candidate = (Candidate) this.flash.get("candidate");
		} else {
			this.candidate = new Candidate();
		}
		if (this.candidate.getAddress() == null) {
			this.candidate.setAddress(new Address());
		}
		this.flash.put("candidate", this.candidate);
	}

	public void insertOrUpdate(Candidate candidate) {
		if (candidate.getId() == 0) {
			this.candidateService.insertCandidate(candidate);
		} else {
			candidate = this.candidateService.updateCandidate(candidate);
		}
	}

	public String save() {
		insertOrUpdate(this.candidate);
		return "candidateSearch?faces-redirect=true";
	}

	public void setCandidate(final Candidate candidate) {
		this.candidate = candidate;
	}
}
