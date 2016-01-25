package ged.web.view.candidate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.faces.application.NavigationHandler;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.service.candidate.Candidate;
import ged.ejb.service.candidate.CandidateService;
import ged.ejb.service.curriculum.Curriculum;
import ged.ejb.service.curriculum.Education;
import ged.ejb.service.curriculum.JobExperience;
import ged.ejb.service.curriculum.Language;
import ged.ejb.service.curriculum.Skill;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class CandidateViewBean extends AbstractBean {

	private static final long serialVersionUID = 1577879781927493283L;

	private String candidateId;

	private Candidate candidate;

	private Curriculum curriculum;

	private List<Education> education = new ArrayList<Education>();

	private List<Skill> skills = new ArrayList<Skill>();

	private List<JobExperience> jobExperiences = new ArrayList<JobExperience>();

	private List<Language> languages = new ArrayList<Language>();
	
	@Inject
	private CandidateService candidateService;

	// /////////////////////////////////////////////////////////////////////////
	// INIT
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Not using @PostConstruct because the view is a GET based form.
	 */
	public void init() {
		if (candidateId != null) {
			try {
				Long id = Long.parseLong(candidateId);
				candidate = candidateService.searchCandidateAndCurriculumById(id);				
				if (candidate == null) {
					error();
				}

				Curriculum curriculum = candidate.getCurriculum();
				if (curriculum != null) {
					if (curriculum.getSkills() == null) {
						curriculum.setSkills(new HashSet<Skill>());
					}
					if (curriculum.getEducation() == null) {
						curriculum.setEducation(new HashSet<Education>());
					}
					if (curriculum.getJobExperiences() == null) {
						curriculum
								.setJobExperiences(new HashSet<JobExperience>());
					}
					if (curriculum.getLanguages() == null) {
						curriculum.setLanguages(new HashSet<Language>());
					}

					skills.addAll(curriculum.getSkills());
					education.addAll(curriculum.getEducation());
					jobExperiences.addAll(curriculum.getJobExperiences());
					languages.addAll(curriculum.getLanguages());
				}

			} catch (NumberFormatException ex) {
				error();
			}
		} else {
			error();
		}
	}

	private void error() {
		NavigationHandler navigationHandler = facesContext.getApplication()
				.getNavigationHandler();
		navigationHandler.handleNavigation(facesContext, null,
				"candidateSearch?faces-redirect=true");
		facesContext.renderResponse();
	}

	// /////////////////////////////////////////////////////////////////////////
	// SET AND GET
	// /////////////////////////////////////////////////////////////////////////

	public Curriculum getCurriculum() {
		return curriculum;
	}

	public void setCurriculum(Curriculum curriculum) {
		this.curriculum = curriculum;
	}

	public List<Education> getEducation() {
		return education;
	}

	public void setEducation(List<Education> education) {
		this.education = education;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

	public List<JobExperience> getJobExperiences() {
		return jobExperiences;
	}

	public void setJobExperiences(List<JobExperience> jobExperiences) {
		this.jobExperiences = jobExperiences;
	}

	public List<Language> getLanguages() {
		return languages;
	}

	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}

	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	// /////////////////////////////////////////////////////////////////////////
	// ACTIONS
	// /////////////////////////////////////////////////////////////////////////

	public String modifyCandidate() {
		flash.put("candidate", candidate);
		return "candidateEdit?faces-redirect=true";
	}

	public String modifyCurriculum() {
		flash.put("curriculum", candidate.getCurriculum());
		return "curriculumEdit?faces-redirect=true";
	}
}
