package ged.ejb.core;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.service.candidate.FileType;
import ged.ejb.service.curriculum.LanguageLevel;
import ged.ejb.service.curriculum.SkillLevel;
import ged.ejb.service.job.ContractDuration;
import ged.ejb.service.job.ContractType;

@Named
@ApplicationScoped
public class Cache implements Serializable {

	private static final long serialVersionUID = -8778037668334921574L;

	private List<ContractType> contractTypes;

	private List<ContractDuration> contractDurations;

	private List<LanguageLevel> languageLevels;

	private List<SkillLevel> skillLevels;

	private List<FileType> fileTypes;

	@Inject
	protected Logger logger;

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	@Inject
	private MaintenanceService maintenanceService;

	@PostConstruct
	public void init() {
		contractTypes = maintenanceService.getAll(ContractType.class);
		contractDurations = maintenanceService.getAll(ContractDuration.class);
		skillLevels = maintenanceService.getAll(SkillLevel.class);
		languageLevels = maintenanceService.getAll(LanguageLevel.class);
		fileTypes = maintenanceService.getAll(FileType.class);
	}

	public List<ContractType> getContractTypes() {
		return contractTypes;
	}

	public List<ContractDuration> getContractDurations() {
		return contractDurations;
	}

	public List<FileType> getFileTypes() {
		return fileTypes;
	}

	public List<LanguageLevel> getLanguageLevels() {
		return languageLevels;
	}

	public List<SkillLevel> getSkillLevels() {
		return skillLevels;
	}
}
