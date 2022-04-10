package es.nivel36.laie.web.view.curriculum;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;
import org.omnifaces.util.Faces;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.curriculum.Curriculum;
import es.nivel36.laie.ejb.curriculum.CurriculumService;
import es.nivel36.laie.ejb.curriculum.CurriculumTemplate;
import es.nivel36.laie.web.core.view.AbstractView;

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

	public void export(final CurriculumTemplate template) throws IOException  {
		final File file = this.curriculumService.export(this.curriculum.getId(), template);
		try (InputStream is = Files.newInputStream(file.toPath())) {
			Faces.sendFile(is, buildFileName(template), true);
		}
	}

	private String buildFileName(final CurriculumTemplate template) {
		return curriculum.getCandidate().getFullName() + "_" + template.getTitle() + ".pdf";
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
