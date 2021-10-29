package es.nivel36.laie.web.view.curriculum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;

import es.nivel36.laie.ejb.curriculum.Curriculum;
import es.nivel36.laie.ejb.curriculum.CurriculumService;
import es.nivel36.laie.ejb.curriculum.skill.Skill;
import es.nivel36.laie.web.core.util.PageEnum;
import es.nivel36.laie.web.core.view.AbstractView;

@Named
@ViewScoped
public class SkillsView extends AbstractView {

	private static final long serialVersionUID = 1L;

	@Inject
	@Param(name = "curriculumId", required = true)
	private Curriculum curriculum;

	@Inject
	private transient CurriculumService curriculumService;

	private List<Skill> skills;

	private String curriculumUrl() {
		final Map<String, String> queryParams = new HashMap<>();
		queryParams.put("id", curriculum.getUid());
		queryParams.put("candidateId", this.curriculum.getCandidate().getUid());
		return this.navigator.getRedirectUrl(PageEnum.CURRICULUM, queryParams);
	}

	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	public List<Skill> getSkills() {
		return this.skills;
	}

	@PostConstruct
	public void init() {
		this.skills = new ArrayList<>(this.curriculum.getSkills());
	}

	public String save() {
		for (final Skill skill : this.skills) {
			skill.setCurriculum(this.curriculum);
		}
		this.curriculum.setSkills(this.skills);
		this.curriculumService.save(this.curriculum);
		return this.curriculumUrl();
	}

	public void setCurriculumService(final CurriculumService curriculumService) {
		this.curriculumService = curriculumService;
	}

	public void setSkills(final List<Skill> skills) {
		this.skills = skills;
	}
}
