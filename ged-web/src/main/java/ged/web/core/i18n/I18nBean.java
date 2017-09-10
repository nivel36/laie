package ged.web.core.i18n;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.Application;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.i18n.I18nService;
import ged.ejb.core.i18n.I18nString;
import ged.web.core.util.TransaltionUtils;
import ged.web.core.view.AbstractPageBean;

@Named
@ApplicationScoped
public class I18nBean extends AbstractPageBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 7203326692219293611L;

	private static final String SPANISH = "es";

	@Inject
	private Application application;

	@Inject
	private I18nService i18nService;

	private Map<String, Map<String, String>> i18nTexts;

	private List<String> locales;

	// TODO: problemas: Si se añade o modifica un registro en la tabla de i18n
	// esto no funciona.
	public String getI18nText(final String key, final String language) {
		String translatedText;
		if (this.i18nTexts.get(language).containsKey(key)) {
			translatedText = this.i18nTexts.get(language).get(key);
		} else {
			try {
				translatedText = TransaltionUtils.translate(key);
			} catch (final MissingResourceException e) {
				translatedText = "?" + key + "?";
			}
		}
		return translatedText;
	}

	private String getLanguageFromDefaultLocale() {
		final Locale defaultLocale = this.application.getDefaultLocale();
		if (defaultLocale == null) {
			return SPANISH;
		} else {
			return defaultLocale.getLanguage();
		}
	}

	@PostConstruct
	public void init() {
		logger.debug("I18nBean init");
		loadLocales();
		loadI18nText();
	}

	private void loadI18nText() {
		this.i18nTexts = new HashMap<>();
		for (final String locale : this.locales) {
			this.i18nTexts.put(locale, new HashMap<String, String>());
		}
		final List<I18nString> allI18nText = this.i18nService.findAll();
		for (final I18nString i18nText : allI18nText) {
			this.i18nTexts.get(i18nText.getLocale()).put(i18nText.getKey(), i18nText.getText());
		}
	}

	private void loadLocales() {
		final Iterator<Locale> supportedLocales = this.application.getSupportedLocales();
		this.locales = new ArrayList<>();
		while (supportedLocales.hasNext()) {
			final String language = supportedLocales.next().getLanguage();
			this.locales.add(language);
		}
		final String language = getLanguageFromDefaultLocale();
		this.locales.add(language);
	}
}
