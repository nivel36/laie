package ged.web.core.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.Application;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.core.Cache;
import ged.ejb.core.FileType;
import ged.ejb.core.Indexer;
import ged.ejb.curriculum.LanguageLevel;
import ged.ejb.curriculum.SkillLevel;
import ged.ejb.user.Role;

@ApplicationScoped
@Named
public class ApplicationBean extends AbstractBean {

	private static final long serialVersionUID = 6394915115616408285L;

	@Inject
	private Cache cache;

	@Inject
	private Indexer indexer;

	private List<Locale> locales = new ArrayList<Locale>();

	@Inject
	protected transient Logger logger;

	private Properties properties;

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
		try {
			loadLocales();
			this.properties = new Properties();
			this.properties.load(ApplicationBean.class.getResourceAsStream("/ged/config.properties"));
			this.indexer.index();
		} catch (final IOException e) {
			this.logger.log(Level.SEVERE, "Could not load properties");
		} catch (final InterruptedException ex) {
			this.logger.log(Level.SEVERE, "Could not index");
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

	public void setLocales(final List<Locale> locales) {
		this.locales = locales;
	}

	public void setLogger(final Logger logger) {
		this.logger = logger;
	}
}
