package ged.web.core.view;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.Application;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.web.core.util.WebConfigurationProperty;

@ApplicationScoped
@Named
public class ApplicationView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1L;

	@Inject
	@WebConfigurationProperty(value = "ged.buildtime")
	private String buildtime;

	private List<Locale> locales = new ArrayList<>();

	@Inject
	@WebConfigurationProperty(value = "ged.version")
	private String version;

	public String getBuildtime() {
		return this.buildtime;
	}

	public List<Locale> getLocales() {
		return this.locales;
	}

	public String getVersion() {
		return this.version;
	}

	@PostConstruct
	public void init() {
		this.loadLocales();
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
