package ged.web.core.i18n;

import javax.faces.context.FacesContext;

import ged.web.core.view.SessionBean;

public final class I18nFaceletsFunction {

	private I18nFaceletsFunction() {
	}

	private static I18nBean getI18nBeanBean() {
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getApplication().evaluateExpressionGet(context,
				"#{i18nBean}", I18nBean.class);
	}

	private static SessionBean getSessionBean() {
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getApplication().evaluateExpressionGet(context,
				"#{sessionBean}", SessionBean.class);
	}

	public static String translate(String key) {
		I18nBean i18nBean = getI18nBeanBean();
		SessionBean sessionBean = getSessionBean();
		String language = sessionBean.getLocale().getLanguage();
		String translatedString = i18nBean.getI18nText(key, language);
		return translatedString;
	}
}
