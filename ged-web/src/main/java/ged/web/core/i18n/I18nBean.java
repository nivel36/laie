package ged.web.core.i18n;

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

	private final static Logger logger = LoggerFactory.getLogger(I18nBean.class.getName());

	private static final long serialVersionUID = 7203326692219293611L;

	@Inject
	private I18nService i18nService;

	private Map<String, Map<String, String>> i18nTexts;

	private List<String> locales;

	public String getI18nText(final String key, final String language) {
		String translatedText;
		if (this.i18nTexts.get(language).containsKey(key)) {
			translatedText = this.i18nTexts.get(language).get(key);
		} else {
			try {
				translatedText = TransaltionUtils.translate(key);
			} catch (final MissingResourceException e) {
				logger.error("Error loading image", e);
				translatedText = "?" + key + "?";
			}
		}
		return translatedText;
	}

	@PostConstruct
	public void init() {
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
		final Application app = this.facesContext.getApplication();
		final Iterator<Locale> supportedLocales = app.getSupportedLocales();
		this.locales = new ArrayList<>();
		while (supportedLocales.hasNext()) {
			final String language = supportedLocales.next().getLanguage();
			this.locales.add(language);
		}
		final Locale defaultLocale = app.getDefaultLocale();
		final String language;
		if (defaultLocale == null) {
			language = "es";
		} else {
			language = defaultLocale.getLanguage();
		}
		this.locales.add(language);
	}
}
