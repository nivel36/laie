package es.nivel36.laie.web.view.curriculum;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

import javax.faces.view.ViewScoped;

import org.omnifaces.cdi.Param;
import org.omnifaces.util.Faces;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.curriculum.Curriculum;
import es.nivel36.laie.ejb.curriculum.CurriculumService;
import es.nivel36.laie.ejb.curriculum.CurriculumTemplate;
import es.nivel36.laie.web.core.view.AbstractView;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class ExportCurriculumView extends AbstractView {

	private static final long serialVersionUID = -2181956059475714175L;

	@Param
	private Candidate candidate;

	@Param
	private Curriculum curriculum;

	@Inject
	private CurriculumService curriculumService;

	private List<CurriculumTemplate> templates;

	public void export(final CurriculumTemplate template) throws IOException {
		final File file = this.curriculumService.export(this.curriculum, template);
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
