package ged.web.view.curriculum;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ged.ejb.curriculum.JobExperience;
import ged.web.core.view.AbstractDialogBean;

@Named
@ViewScoped
public class JobExperienceDialogBean extends AbstractDialogBean {

	private static final long serialVersionUID = -2896828087283592020L;

	private JobExperience jobExperience;

	@PostConstruct
	public void init() {
		final long jobExperienceId = this.getIdFromParameters("jobExperienceId");

	}
}
