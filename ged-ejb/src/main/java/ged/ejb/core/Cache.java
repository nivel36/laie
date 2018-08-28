package ged.ejb.core;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import ged.ejb.curriculum.CurriculumService;
import ged.ejb.curriculum.language.LanguageLevel;
import ged.ejb.curriculum.skills.SkillLevel;
import ged.ejb.user.UserService;
import ged.ejb.user.role.Role;

@ApplicationScoped
public class Cache implements Serializable {

	private static final long serialVersionUID = -8778037668334921574L;

	@Inject
	private transient CurriculumService curriculumService;

	private List<LanguageLevel> languageLevels;

	private List<Role> roles;

	private List<SkillLevel> skillLevels;

	@Inject
	private transient UserService userService;

	public List<LanguageLevel> getLanguageLevels() {
		return this.languageLevels;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public List<SkillLevel> getSkillLevels() {
		return this.skillLevels;
	}

	@PostConstruct
	public void init() {
		this.skillLevels = this.curriculumService.findAllSkillLevels();
		this.languageLevels = this.curriculumService.findAllLanguageLevels();
		this.roles = this.userService.findAllRoles();
	}
}
