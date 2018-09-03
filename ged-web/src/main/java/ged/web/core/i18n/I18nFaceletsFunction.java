package ged.web.core.i18n;

import javax.faces.context.FacesContext;

import ged.web.core.view.SessionUser;

public final class I18nFaceletsFunction {

	private static I18nBean getI18nBeanBean() {
		final FacesContext context = FacesContext.getCurrentInstance();
		return context.getApplication().evaluateExpressionGet(context, "#{i18nBean}", I18nBean.class);
	}

	private static SessionUser getSessionBean() {
		final FacesContext context = FacesContext.getCurrentInstance();
		return context.getApplication().evaluateExpressionGet(context, "#{sessionBean}", SessionUser.class);
	}

	public static String translate(final String key) {
		final I18nBean i18nBean = getI18nBeanBean();
		final SessionUser sessionBean = getSessionBean();
		final String language = sessionBean.getLocale().getLanguage();
		return i18nBean.getI18nText(key, language);
	}

	private I18nFaceletsFunction() {
	}
}