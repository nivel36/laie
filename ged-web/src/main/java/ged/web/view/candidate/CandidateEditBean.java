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
import ged.web.core.view.AbstractPageBean;

@Named
@ViewScoped
public class CandidateEditBean extends AbstractPageBean {

	private static final long serialVersionUID = -4334616177754425866L;

	private Candidate candidate;

	@Inject
	private transient CandidateService candidateService;

	@Inject
	private transient CurriculumService curriculumService;

	public String cancel() {
		return "candidateSearch?faces-redirect=true";
	}

	private Curriculum createNewCurriculum() {
		final Curriculum curriculum = new Curriculum();
		curriculum.setCandidate(this.candidate);
		return curriculum;
	}

	public String editCurriculum() {
		Curriculum curriculum = null;
		if (this.candidate.getId() == null) {
			this.candidate.setOwner(this.sessionBean.getUser());
			this.candidate.setUser(this.sessionBean.getUser());
			this.candidateService.insert(this.candidate);
			curriculum = createNewCurriculum();
		} else {
			this.candidate.setUser(this.sessionBean.getUser());
			this.candidate = this.candidateService.update(this.candidate);
			curriculum = findCurriculum(this.candidate.getId());
		}
		this.flash.put("curriculum", curriculum);
		return "curriculumEdit?faces-redirect=true";
	}

	private Curriculum findCurriculum(final Long id) {
		Curriculum curriculum = this.curriculumService.findByCandidateId(id);
		if (curriculum == null) {
			curriculum = createNewCurriculum();
		}
		return curriculum;
	}

	public Candidate getCandidate() {
		return this.candidate;
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

	public String save() {
		saveCandidate();
		return "candidateSearch?faces-redirect=true";
	}

	private void saveCandidate() {
		this.candidate.setUser(this.sessionBean.getUser());
		this.candidate = this.candidateService.insertOrUpdate(this.candidate);
	}

	public void setCandidate(final Candidate candidate) {
		this.candidate = candidate;
	}
}
