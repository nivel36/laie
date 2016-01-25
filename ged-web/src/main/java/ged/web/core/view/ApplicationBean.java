package ged.web.core.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.Application;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.core.MaintenanceService;
import ged.ejb.service.curriculum.LanguageLevel;
import ged.ejb.service.curriculum.SkillLevel;
import ged.ejb.service.job.ContractDuration;
import ged.ejb.service.job.ContractType;

@Named
@ApplicationScoped
public class ApplicationBean extends AbstractBean {

	private static final long serialVersionUID = -8778037668334921574L;

	private List<ContractType> contractTypes;

	private List<ContractDuration> contractDurations;

	private List<LanguageLevel> languageLevels;

	private List<SkillLevel> skillLevels;

	private List<Locale> locales = new ArrayList<Locale>();
	
	@Inject
	private MaintenanceService maintenanceService;

	@PostConstruct
	public void init() {
		contractTypes = maintenanceService.getAll(ContractType.class);
		contractDurations = maintenanceService.getAll(ContractDuration.class);
		skillLevels = maintenanceService.getAll(SkillLevel.class);
		languageLevels = maintenanceService.getAll(LanguageLevel.class);
		loadLocales();
	}

	private void loadLocales() {
		Application app = facesContext.getApplication();
		Iterator<Locale> supportedLocales = app.getSupportedLocales();
		while (supportedLocales.hasNext()) {
			locales.add(supportedLocales.next());
		}
		Locale defaultLocale = app.getDefaultLocale();
		locales.add(defaultLocale);
	}

	public List<ContractType> getContractTypes() {
		return contractTypes;
	}

	public List<ContractDuration> getContractDurations() {
		return contractDurations;
	}

	public List<LanguageLevel> getLanguageLevels() {
		return languageLevels;
	}

	public List<SkillLevel> getSkillLevels() {
		return skillLevels;
	}

	public List<Locale> getLocales() {
		return locales;
	}
}
