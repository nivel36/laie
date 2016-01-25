package ged.web.core.i18n;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import ged.web.core.view.SessionBean;

@FacesConverter(value = "i18nConverter")
public class I18nConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		String language = getSessionBean().getUser().getLanguage();
		return getI18nBeanBean().getI18nText((String) value, language);
	}

	protected I18nBean getI18nBeanBean() {
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getApplication().evaluateExpressionGet(context,
				"#{i18nBean}", I18nBean.class);
	}

	protected SessionBean getSessionBean() {
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getApplication().evaluateExpressionGet(context,
				"#{sessionBean}", SessionBean.class);
	}
}
