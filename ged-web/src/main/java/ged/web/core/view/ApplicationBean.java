package ged.web.core.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.Application;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.core.Cache;
import ged.ejb.service.curriculum.LanguageLevel;
import ged.ejb.service.curriculum.SkillLevel;
import ged.ejb.service.job.ContractDuration;
import ged.ejb.service.job.ContractType;

@ApplicationScoped
@Named
public class ApplicationBean extends AbstractBean {

	private static final long serialVersionUID = 6394915115616408285L;
	
	@Inject
	private Cache cache;

	public Cache getCache() {
		return cache;
	}

	public void setCache(Cache cache) {
		this.cache = cache;
	}

	public List<Locale> getLocales() {
		return locales;
	}

	public void setLocales(List<Locale> locales) {
		this.locales = locales;
	}

	private List<Locale> locales = new ArrayList<Locale>();

	private Properties properties;

	public List<ContractDuration> getContractDurations() {
		return cache.getContractDurations();
	}

	public List<ContractType> getContractTypes() {
		return cache.getContractTypes();
	}

	public List<LanguageLevel> getLanguageLevels() {
		return cache.getLanguageLevels();
	}

	public Properties getProperties() {
		return properties;
	}

	public List<SkillLevel> getSkillLevels() {
		return cache.getSkillLevels();
	}

	@PostConstruct
	public void init() {
		loadLocales();
		properties = new Properties();
		try {
			properties.load(ApplicationBean.class
					.getResourceAsStream("/ged/config.properties"));
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Could not load properties");
		}
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
}
