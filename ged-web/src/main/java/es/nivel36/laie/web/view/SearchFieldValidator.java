package es.nivel36.laie.web.view;

import java.lang.invoke.MethodHandles;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesValidator(managed = true, value = "es.nivel36.laie.web.view.SearchFieldValidator")
public class SearchFieldValidator implements Validator<String> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private ResourceBundle getResourceBundle(final FacesContext context, final String filename) {
		final Locale locale = context.getViewRoot().getLocale();
		return ResourceBundle.getBundle(filename, locale);
	}

	protected String translate(final FacesContext context, final String message) {
		final ResourceBundle bundle = this.getResourceBundle(context, "ged.i18n");
		return bundle.getString(message);
	}

	@Override
	public void validate(final FacesContext context, final UIComponent component, final String value) {
		if ((value != null) && (value.length() < 3)) {
			logger.warn("Search value is too short");
			final String translatedMessage = this.translate(context, "error.search.camp_too_short");
			final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, translatedMessage,
					translatedMessage);
			throw new ValidatorException(message);
		}
	}
}
