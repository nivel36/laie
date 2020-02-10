package ged.web.view.curriculum;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;

import ged.ejb.candidate.Candidate;
import ged.ejb.curriculum.Curriculum;
import ged.ejb.curriculum.CurriculumTemplate;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class ExportCurriculumView extends AbstractView {

	private static final String CANDIDATE_ID = "candidateId";

	private static final String CURRICULUM_ID = "curriculumId";

	private static final String ID = "id";

	private static final long serialVersionUID = 1L;

	@Inject
	@Param(name = CANDIDATE_ID, required = false)
	private Candidate candidate;

	@Inject
	@Param(name = ID, required = false)
	private Curriculum curriculum;

	public void export(CurriculumTemplate curriculumTemplate) {

	}
}
