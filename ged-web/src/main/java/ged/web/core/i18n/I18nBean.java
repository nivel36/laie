package ged.web.core.i18n;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.i18n.I18nService;
import ged.ejb.core.i18n.I18nString;
import ged.web.core.view.AbstractBean;

@Named
@ApplicationScoped
public class I18nBean extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 7203326692219293611L;

	private static final String SPANISH = "es";

	@Inject
	private transient I18nService i18nService;

	private final List<String> locales = new ArrayList<>();

	public String getI18nText(final String key, final String language) {
		try {
			return this.translator.message(key);
		}
		catch (final MissingResourceException e) {
			final I18nString message = this.i18nService.find(key, language);
			if (message != null) {
				return message.getText();
			}
			else {
				return "?" + key + "?";
			}
		}
	}

	private String getLanguageFromDefaultLocale() {
		final Locale defaultLocale = this.application.getDefaultLocale();
		if (defaultLocale == null) {
			return SPANISH;
		}
		else {
			return defaultLocale.getLanguage();
		}
	}

	@PostConstruct
	public void init() {
		logger.debug("I18nBean init");
		this.loadLocales();
	}

	private void loadLocales() {
		String language = this.getLanguageFromDefaultLocale();
		this.locales.add(language);

		final Iterator<Locale> supportedLocales = this.application.getSupportedLocales();
		while (supportedLocales.hasNext()) {
			language = supportedLocales.next().getLanguage();
			this.locales.add(language);
		}
	}
}
