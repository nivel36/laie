package ged.web.view.candidate;

import static ged.web.core.util.Navigate.to;
import static ged.web.core.util.Page.CURRICULUM;

import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.curriculum.Curriculum;
import ged.ejb.curriculum.CurriculumService;
import ged.ejb.curriculum.Education;
import ged.ejb.curriculum.JobExperience;
import ged.ejb.curriculum.Language;
import ged.ejb.curriculum.Skill;
import ged.web.core.PageNotFoundException;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class CurriculumViewBean extends AbstractBean {

	private static final long serialVersionUID = -5942086439519787220L;

	private Candidate candidate;

	@Inject
	private transient CandidateService candidateService;

	private Curriculum curriculum;

	@Inject
	private transient CurriculumService curriculumService;

	private List<Education> education;

	private String id;

	private List<JobExperience> jobExperiences;

	private List<Language> languages;

	private List<Skill> skills;

	public String editCurriculum() {
		this.flash.put("curriculum", this.curriculum);
		return to(CURRICULUM).toUrl();
	}

	public Candidate getCandidate() {
		return this.candidate;
	}

	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	public List<Education> getEducation() {
		return this.education;
	}

	public String getId() {
		return this.id;
	}

	public List<JobExperience> getJobExperiences() {
		return this.jobExperiences;
	}

	public List<Language> getLanguages() {
		return this.languages;
	}

	public List<Skill> getSkills() {
		return this.skills;
	}

	public void init() {
		if (this.id != null) {
			try {
				final long candidateId = Long.parseLong(this.id);
				this.candidate = this.candidateService.find(candidateId);
				if (this.candidate == null) {
					throw new PageNotFoundException();
				}
				this.curriculum = this.curriculumService.findByCandidate(this.candidate);
				if (this.curriculum == null) {
					throw new PageNotFoundException();
				}
				this.jobExperiences = new ArrayList<>(this.curriculum.getJobExperiences());
				this.skills = new ArrayList<>(this.curriculum.getSkills());
				this.education = new ArrayList<>(this.curriculum.getEducation());
				this.languages = new ArrayList<>(this.curriculum.getLanguages());
			}
			catch (final NumberFormatException ex) {
				throw new PageNotFoundException();
			}
		}
		else {
			throw new PageNotFoundException();
		}
	}

	public void setCurriculumService(final CurriculumService curriculumService) {
		this.curriculumService = curriculumService;
	}

	public void setId(final String id) {
		this.id = id;
	}
}