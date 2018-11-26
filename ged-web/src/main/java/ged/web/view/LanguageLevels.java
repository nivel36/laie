package ged.web.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.curriculum.CurriculumService;
import ged.ejb.curriculum.language.LanguageLevel;

@ApplicationScoped
@Named
public class LanguageLevels implements Serializable {

	private static final long serialVersionUID = -8778037668334921574L;

	@Inject
	private transient CurriculumService curriculumService;

	private List<LanguageLevel> languageLevels;

	public List<LanguageLevel> getList() {
		return this.languageLevels;
	}

	@PostConstruct
	public void init() {
		this.languageLevels = this.curriculumService.findAllLanguageLevels();
	}

	public void setCurriculumService(final CurriculumService curriculumService) {
		this.curriculumService = curriculumService;
	}
}
