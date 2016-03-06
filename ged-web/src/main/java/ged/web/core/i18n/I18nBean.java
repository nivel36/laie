package ged.web.core.i18n;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.Application;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.core.i18n.I18nService;
import ged.ejb.core.i18n.I18nString;
import ged.web.core.view.AbstractBean;

@Named
@ApplicationScoped
public class I18nBean extends AbstractBean {

	private static final long serialVersionUID = 7203326692219293611L;

	@Inject
	private I18nService i18nService;

	private Map<String, Map<String, String>> i18nTexts;

	private List<String> locales;

	public String getI18nText(final String key, final String language) {
		String translatedText = null;
		if (this.i18nTexts.get(language).containsKey(key)) {
			translatedText = this.i18nTexts.get(language).get(key);
		} else {
			try {
				translatedText = translate(key);
			} catch (final MissingResourceException e) {
				translatedText = "?" + key + "?";
			}
		}
		return translatedText;
	}

	private ResourceBundle getResourceBundle(final String filename) {
		final Locale locale = this.facesContext.getViewRoot().getLocale();
		final ResourceBundle bundle = ResourceBundle.getBundle(filename, locale);
		return bundle;
	}

	@PostConstruct
	public void init() {
		loadLocales();
		loadI18nText();
	}

	private void loadI18nText() {
		this.i18nTexts = new HashMap<String, Map<String, String>>();
		for (final String locale : this.locales) {
			this.i18nTexts.put(locale, new HashMap<String, String>());
		}
		final List<I18nString> allI18nText = this.i18nService.findAll();
		for (final I18nString i18nText : allI18nText) {
			this.i18nTexts.get(i18nText.getLocale()).put(i18nText.getKey(), i18nText.getText());
		}
	}

	private void loadLocales() {
		final Application app = this.facesContext.getApplication();
		final Iterator<Locale> supportedLocales = app.getSupportedLocales();
		this.locales = new ArrayList<String>();
		while (supportedLocales.hasNext()) {
			final String language = supportedLocales.next().getLanguage();
			this.locales.add(language);
		}
		final Locale defaultLocale = app.getDefaultLocale();
		String language = null;
		if (defaultLocale == null) {
			language = "es";
		} else {
			language = defaultLocale.getLanguage();
		}
		this.locales.add(language);
	}

	private String translate(final String message) {
		final ResourceBundle bundle = getResourceBundle("ged.i18n");
		final String translatedMessage = bundle.getString(message);
		return translatedMessage;
	}
}
