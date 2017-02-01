package ged.web.core.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.Application;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.core.Cache;
import ged.ejb.core.FileType;
import ged.ejb.core.util.ConfigurationProperty;
import ged.ejb.curriculum.LanguageLevel;
import ged.ejb.curriculum.SkillLevel;
import ged.ejb.user.role.Role;

@ApplicationScoped
@Named
public class ApplicationBean extends AbstractBean {

	private static final long serialVersionUID = 6394915115616408285L;

	@Inject
	@ConfigurationProperty(value = "ged.buildtime")
	private String buildtime;

	@Inject
	private Cache cache;

	private List<Locale> locales = new ArrayList<>();

	@Inject
	protected transient Logger logger;

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

	public void setLogger(final Logger logger) {
		this.logger = logger;
	}

	public void setVersion(final String version) {
		this.version = version;
	}
}
