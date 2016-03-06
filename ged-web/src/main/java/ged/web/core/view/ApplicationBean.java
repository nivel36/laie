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

import ged.ejb.candidate.FileType;
import ged.ejb.core.Cache;
import ged.ejb.curriculum.LanguageLevel;
import ged.ejb.curriculum.SkillLevel;
import ged.ejb.user.Role;

@ApplicationScoped
@Named
public class ApplicationBean extends AbstractBean {

	private static final long serialVersionUID = 6394915115616408285L;

	@Inject
	private Cache cache;

	private List<Locale> locales = new ArrayList<Locale>();

	private Properties properties;

	public Cache getCache() {
		return this.cache;
	}

	public List<FileType> getFileTypes() {
		return this.cache.getFileTypes();
	}

	public List<LanguageLevel> getLanguageLevels() {
		return this.cache.getLanguageLevels();
	}

	public List<Locale> getLocales() {
		return this.locales;
	}

	public Properties getProperties() {
		return this.properties;
	}

	public List<Role> getRoles() {
		return this.cache.getRoles();
	}

	public List<SkillLevel> getSkillLevels() {
		return this.cache.getSkillLevels();
	}

	@PostConstruct
	public void init() {
		loadLocales();
		this.properties = new Properties();
		try {
			this.properties.load(ApplicationBean.class.getResourceAsStream("/ged/config.properties"));
		} catch (final Exception e) {
			this.logger.log(Level.SEVERE, "Could not load properties");
		}
	}

	private void loadLocales() {
		final Application app = this.facesContext.getApplication();
		final Iterator<Locale> supportedLocales = app.getSupportedLocales();
		while (supportedLocales.hasNext()) {
			this.locales.add(supportedLocales.next());
		}
		final Locale defaultLocale = app.getDefaultLocale();
		this.locales.add(defaultLocale);
	}

	public void setCache(final Cache cache) {
		this.cache = cache;
	}

	public void setLocales(final List<Locale> locales) {
		this.locales = locales;
	}
}
