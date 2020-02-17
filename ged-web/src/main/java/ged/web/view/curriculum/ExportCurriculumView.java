package ged.web.view.curriculum;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;
import org.omnifaces.util.Faces;

import ged.ejb.candidate.Candidate;
import ged.ejb.curriculum.Curriculum;
import ged.ejb.curriculum.CurriculumService;
import ged.ejb.curriculum.CurriculumTemplate;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class ExportCurriculumView extends AbstractView {

	private static final String CANDIDATE_ID = "candidateId";

	private static final String ID = "id";

	private static final long serialVersionUID = 1L;

	@Inject
	@Param(name = CANDIDATE_ID, required = false)
	private Candidate candidate;

	@Inject
	@Param(name = ID, required = false)
	private Curriculum curriculum;

	@Inject
	private CurriculumService curriculumService;

	private List<CurriculumTemplate> templates;

	public void export(final CurriculumTemplate template) throws Exception {
		final File file = this.curriculumService.export(this.curriculum, template);
		Faces.sendFile(file, true);
	}

	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	public List<CurriculumTemplate> getTemplates() {
		return this.templates;
	}

	@PostConstruct
	public void init() {
		this.templates = curriculumService.findCurriculumTemplates();
	}
}
