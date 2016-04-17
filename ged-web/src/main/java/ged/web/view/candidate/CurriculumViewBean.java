package ged.web.view.candidate;

import javax.faces.application.NavigationHandler;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.curriculum.Curriculum;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class CurriculumViewBean extends AbstractBean {

	private static final long serialVersionUID = -5942086439519787220L;

	private Candidate candidate;

	@Inject
	private transient CandidateService candidateService;

	private Curriculum curriculum;

	private String id;

	private void error() {
		final NavigationHandler navigationHandler = this.facesContext.getApplication().getNavigationHandler();
		navigationHandler.handleNavigation(this.facesContext, null, "candidateSearch?faces-redirect=true");
		this.facesContext.renderResponse();
	}

	public Candidate getCandidate() {
		return this.candidate;
	}

	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	public String getId() {
		return this.id;
	}

	public void init() {
		if (this.id != null) {
			try {
				final Long id = Long.parseLong(this.id);
				this.candidate = this.candidateService.findCandidateAndCurriculumById(id);
				if (this.candidate == null) {
					error();
				}
				if (this.candidate.getCurriculum() == null) {
					error();
				}
				this.curriculum = this.candidate.getCurriculum();
			} catch (final NumberFormatException ex) {
				error();
			}
		} else {
			error();
		}
	}

	public void setCandidateService(final CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public void setId(final String id) {
		this.id = id;
	}

}
