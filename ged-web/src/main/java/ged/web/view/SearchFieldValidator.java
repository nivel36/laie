package ged.web.view;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

import org.slf4j.Logger;

@FacesValidator("ged.web.view.SearchFieldValidator")
public class SearchFieldValidator implements Validator {

	@Inject
	protected FacesContext facesContext;

	@Inject
	protected transient Logger logger;

	private ResourceBundle getResourceBundle(final String filename) {
		final Locale locale = this.facesContext.getViewRoot().getLocale();
		final ResourceBundle bundle = ResourceBundle.getBundle(filename, locale);
		return bundle;
	}

	protected String translate(final String message) {
		final ResourceBundle bundle = getResourceBundle("ged.i18n");
		final String translatedMessage = bundle.getString(message);
		return translatedMessage;
	}

	@Override
	public void validate(final FacesContext context, final UIComponent component, final Object value)
			throws ValidatorException {
		final String searchValue = (String) value;
		if (searchValue != null && searchValue.length() < 3) {
			this.logger.warn("Search value is too short");
			final String translatedMessage = translate("error.search.camp_too_short");
			final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, translatedMessage,
					translatedMessage);
			throw new ValidatorException(message);
		}
	}
}
