package ged.web.core.i18n;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.Application;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.core.MaintenanceService;
import ged.ejb.core.i18n.I18nString;
import ged.web.core.view.AbstractBean;

@Named
@ApplicationScoped
public class I18nBean extends AbstractBean {

	private static final long serialVersionUID = 7203326692219293611L;

	private List<String> locales;

	private Map<String, Map<String, String>> i18nTexts;

	@Inject
	private MaintenanceService maintenanceService;

	@PostConstruct
	public void init() {
		loadLocales();
		loadI18nText();
	}

	private void loadLocales() {
		Application app = facesContext.getApplication();
		Iterator<Locale> supportedLocales = app.getSupportedLocales();
		locales = new ArrayList<String>();
		while (supportedLocales.hasNext()) {
			String language = supportedLocales.next().getLanguage();
			locales.add(language);
		}
		Locale defaultLocale = app.getDefaultLocale();
		String language = null;
		if (defaultLocale == null) {
			language = "es";
		} else {
			language = defaultLocale.getLanguage();
		}
		locales.add(language);
	}

	private void loadI18nText() {
		i18nTexts = new HashMap<String, Map<String, String>>();
		for (String locale : locales) {
			i18nTexts.put(locale, new HashMap<String, String>());
		}
		List<I18nString> allI18nText = maintenanceService
				.getAll(I18nString.class);
		for (I18nString i18nText : allI18nText) {
			i18nTexts.get(i18nText.getLocale()).put(i18nText.getKey(),
					i18nText.getText());
		}
	}

	public String getI18nText(String key, String language) {
		return i18nTexts.get(language).get(key);
	}
}
