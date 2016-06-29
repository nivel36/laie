package ged.ejb.core;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.candidate.CandidateService;
import ged.ejb.curriculum.CurriculumService;
import ged.ejb.curriculum.LanguageLevel;
import ged.ejb.curriculum.SkillLevel;
import ged.ejb.user.Role;
import ged.ejb.user.service.RoleService;

@Named
@ApplicationScoped
public class Cache implements Serializable {

	private static final long serialVersionUID = -8778037668334921574L;

	@Inject
	private CandidateService candidateService;

	@Inject
	private CurriculumService curriculumService;

	private List<FileType> fileTypes;

	private List<LanguageLevel> languageLevels;

	private List<Role> roles;

	@Inject
	private RoleService roleService;

	private List<SkillLevel> skillLevels;

	public List<FileType> getFileTypes() {
		return this.fileTypes;
	}

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
		this.fileTypes = this.candidateService.findAllFileTypes();
		this.roles = this.roleService.findAllRoles();
	}
}
