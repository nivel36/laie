package ged.web.view.curriculum;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.curriculum.jobexperience.JobExperience;
import ged.ejb.curriculum.jobexperience.JobExperienceService;
import ged.web.core.view.AbstractDialogBean;

@Named
@ViewScoped
public class JobExperienceDialogBean extends AbstractDialogBean {

	private static final long serialVersionUID = -2896828087283592020L;

	private JobExperience jobExperience;

	@Inject
	private transient JobExperienceService jobExperienceService;

	public void delete() {
		if (!this.isNewJobExperience()) {
			this.jobExperienceService.delete(this.jobExperience);
		}
		this.closeDialog();
	}

	public JobExperience getJobExperience() {
		return this.jobExperience;
	}

	@PostConstruct
	public void init() {
		final Long jobExperienceId = this.getIdFromParameters("jobExperienceId");
		if (jobExperienceId != null) {
			this.jobExperience = this.jobExperienceService.find(jobExperienceId);
		}
		if (this.jobExperience == null) {
			this.jobExperience = new JobExperience();
		}
	}

	public boolean isNewJobExperience() {
		return this.jobExperience.getId() == 0;
	}

	public void save() {
		if (this.isNewJobExperience()) {
			this.jobExperienceService.insert(this.jobExperience);
		}
		else {
			this.jobExperience = this.jobExperienceService.update(this.jobExperience);
		}
		this.closeDialog(this.jobExperience);
	}

	public void setJobExperience(final JobExperience jobExperience) {
		this.jobExperience = jobExperience;
	}

	public void setJobExperienceService(final JobExperienceService jobExperienceService) {
		this.jobExperienceService = jobExperienceService;
	}
}
