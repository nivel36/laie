package ged.web.core.i18n;

import javax.faces.context.FacesContext;

import ged.web.core.view.SessionUser;

public final class I18nFaceletsFunction {

	private static I18nView getI18nView() {
		final FacesContext context = FacesContext.getCurrentInstance();
		return context.getApplication().evaluateExpressionGet(context, "#{i18nView}", I18nView.class);
	}

	private static SessionUser getSessionUser() {
		final FacesContext context = FacesContext.getCurrentInstance();
		return context.getApplication().evaluateExpressionGet(context, "#{sessionUser}", SessionUser.class);
	}

	public static String translate(final String key) {
		if ((key == null) || key.isBlank()) {
			return null;
		}
		final I18nView i18nView = getI18nView();
		final SessionUser sessionUser = getSessionUser();
		final String language = sessionUser.getLocale().getLanguage();
		return i18nView.getI18nText(key, language);
	}

	private I18nFaceletsFunction() {
	}
}