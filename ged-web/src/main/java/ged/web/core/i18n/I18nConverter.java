package ged.web.core.i18n;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import ged.web.core.view.SessionBean;

@FacesConverter(value = "i18nConverter")
public class I18nConverter implements Converter<String> {

	@Override
	public String getAsObject(final FacesContext context, final UIComponent component, final String value) {
		return value;
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final String value) {
		final String language = this.getSessionBean().getUser().getLanguage();
		return this.getI18nBeanBean().getI18nText(value, language);
	}

	protected I18nBean getI18nBeanBean() {
		final FacesContext context = FacesContext.getCurrentInstance();
		return context.getApplication().evaluateExpressionGet(context, "#{i18nBean}", I18nBean.class);
	}

	protected SessionBean getSessionBean() {
		final FacesContext context = FacesContext.getCurrentInstance();
		return context.getApplication().evaluateExpressionGet(context, "#{sessionBean}", SessionBean.class);
	}
}
