package ged.web.view.curriculum;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.curriculum.Curriculum;
import ged.ejb.curriculum.CurriculumService;
import ged.ejb.curriculum.Skill;
import ged.web.core.util.PageEnum;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class SkillsView extends AbstractView {

	private static final String CURRICULUM_KEY = "curriculum";

	private static final long serialVersionUID = -5470807754278944272L;

	private Curriculum curriculum;

	@Inject
	private transient CurriculumService curriculumService;

	private List<Skill> skills;

	private String curriculumUrl() {
		return PageEnum.CURRICULUM.getRedirectedUrl(this.curriculum.getCandidate());
	}

	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	public List<Skill> getSkills() {
		return this.skills;
	}

	@PostConstruct
	public void init() {
		this.curriculum = this.getValueFromFlash(CURRICULUM_KEY);
		this.skills = new ArrayList<Skill>(this.curriculum.getSkills());
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
