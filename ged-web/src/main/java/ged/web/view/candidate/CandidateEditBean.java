package ged.web.view.candidate;

import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.core.Address;
import ged.ejb.curriculum.Curriculum;
import ged.ejb.curriculum.CurriculumService;
import ged.web.core.view.AbstractPageBean;

@Named
@ViewScoped
public class CandidateEditBean extends AbstractPageBean {

	private static final Logger logger = LoggerFactory.getLogger(CandidateEditBean.class.getName());

	private static final long serialVersionUID = -4334616177754425866L;

	private Candidate candidate;

	private final transient CandidateService candidateService;

	private final transient CurriculumService curriculumService;

	@Inject
	public CandidateEditBean(final CandidateService candidateService, final CurriculumService curriculumService) {
		Objects.requireNonNull(candidateService);
		Objects.requireNonNull(curriculumService);
		this.candidateService = candidateService;
		this.curriculumService = curriculumService;
	}

	public String cancel() {
		if (this.candidate.getId() == 0) {
			return "candidateSearch?&faces-redirect=true";
		} else {
			return "candidateView?id=" + this.candidate.getId() + "&faces-redirect=true";
		}
	}

	private Curriculum createNewCurriculum() {
		final Curriculum curriculum = new Curriculum();
		curriculum.setCandidate(this.candidate);
		return curriculum;
	}

	public String editCurriculum() {
		Curriculum curriculum = null;
		if (this.candidate.getId() == 0) {
			this.candidate.setOwner(this.sessionBean.getUser());
			this.candidateService.save(this.candidate);
			curriculum = createNewCurriculum();
		} else {
			this.candidate = this.candidateService.save(this.candidate);
			curriculum = findCurriculum(this.candidate.getId());
		}
		this.flash.put("curriculum", curriculum);
		return "curriculumEdit?faces-redirect=true";
	}

	private Curriculum findCurriculum(final long id) {
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
		logger.debug("Save candidate action performed");
		saveCandidate();
		return "candidateView.xhtml?id=" + this.candidate.getId() + "&faces-redirect=true";
	}

	private void saveCandidate() {
		if (this.candidate.getOwner() == null) {
			this.candidate.setOwner(this.sessionBean.getUser());
		}
		this.candidate = this.candidateService.save(this.candidate);
	}

	public void setCandidate(final Candidate candidate) {
		this.candidate = candidate;
	}
}