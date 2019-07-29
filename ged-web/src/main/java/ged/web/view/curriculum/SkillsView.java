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
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class SkillsView extends AbstractView {

	private static final long serialVersionUID = -5470807754278944272L;

	private Curriculum curriculum;

	@Inject
	private transient CurriculumService curriculumService;

	private String skillName;

	private List<String> skills;

	public void addSkill() {
		for (final Skill skill : this.curriculum.getSkills()) {
			if (skill.getName().equalsIgnoreCase(this.skillName)) {
				return;
			}
		}
		final Skill skill = new Skill();
		skill.setName(this.skillName);
		skill.setCurriculum(this.curriculum);
		this.curriculum.addSkill(skill);
		this.saveCurriculum();
		this.skillName = null;
		this.buildSkills();
	}

	private void buildSkills() {
		this.skills = new ArrayList<>();
		for (final Skill skill : this.curriculum.getSkills()) {
			this.skills.add(skill.getName());
		}
	}

	public String getSkillName() {
		return this.skillName;
	}

	public List<String> getSkills() {
		return this.skills;
	}

	@PostConstruct
	public void init() {
		final Long curriculumId = this.getIdFromParameters("curriculumId");
		if (curriculumId == null) {
			this.curriculum = new Curriculum();
		}
		else {
			this.curriculum = this.curriculumService.find(curriculumId);
		}
		this.buildSkills();
	}

	private void saveCurriculum() {
		this.curriculum = this.curriculumService.save(this.curriculum);
	}

	public void setCurriculumService(final CurriculumService curriculumService) {
		this.curriculumService = curriculumService;
	}

	public void setSkillName(final String skillName) {
		this.skillName = skillName;
	}

	public void setSkills(final List<String> skills) {
		this.skills = skills;
	}
}
