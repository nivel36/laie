package ged.web.core.view;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.Application;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.core.Cache;
import ged.ejb.core.FileType;
import ged.ejb.curriculum.LanguageLevel;
import ged.ejb.curriculum.SkillLevel;
import ged.ejb.user.role.Role;
import ged.web.core.util.ConfigurationProperty;

@ApplicationScoped
@Named
public class ApplicationBean extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 6394915115616408285L;

	@Inject
	@ConfigurationProperty(value = "ged.buildtime")
	private String buildtime;

	@Inject
	private Cache cache;

	private List<Locale> locales = new ArrayList<>();

	@Inject
	@ConfigurationProperty(value = "ged.version")
	private String version;

	public String getBuildtime() {
		return this.buildtime;
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

	public List<Role> getRoles() {
		return this.cache.getRoles();
	}

	public List<SkillLevel> getSkillLevels() {
		return this.cache.getSkillLevels();
	}

	public String getVersion() {
		return this.version;
	}

	@PostConstruct
	public void init() {
		loadLocales();
	}

	private void loadLocales() {
		logger.info("Loading locales");
		final Application app = this.facesContext.getApplication();
		final Iterator<Locale> supportedLocales = app.getSupportedLocales();
		while (supportedLocales.hasNext()) {
			this.locales.add(supportedLocales.next());
		}
		final Locale defaultLocale = app.getDefaultLocale();
		this.locales.add(defaultLocale);
	}

	public void setBuildtime(final String buildtime) {
		this.buildtime = buildtime;
	}

	public void setLocales(final List<Locale> locales) {
		this.locales = locales;
	}

	public void setVersion(final String version) {
		this.version = version;
	}
}
