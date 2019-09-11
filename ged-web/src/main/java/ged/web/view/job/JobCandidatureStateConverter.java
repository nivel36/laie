package ged.web.view.job;

import javax.faces.convert.FacesConverter;

import ged.ejb.core.AbstractService;
import ged.ejb.job.candidature.JobCandidatureState;
import ged.web.core.view.AbstractConverter;

@FacesConverter(managed = true, forClass = JobCandidatureState.class)
public class JobCandidatureStateConverter extends AbstractConverter<JobCandidatureState> {

	@Override
	protected AbstractService<JobCandidatureState> getService() {
		return null;
	}

}
