package es.nivel36.laie.web.core.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;

import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.FacesContext;

public class Translator {

	private static final String FILE_NAME = "es.nivel36.laie.i18n";
	
	private static final Locale SPANISH = Locale.of("ES");

	private Locale getLocale() {
		final UIViewRoot uIViewRoot = FacesContext.getCurrentInstance().getViewRoot();
		final Locale locale;
		if (uIViewRoot != null) {
			locale = uIViewRoot.getLocale();
		} else {
			locale = SPANISH;
		}
		return locale;
	}

	private ResourceBundle getResourceBundle(final String filename) {
		final Locale locale = this.getLocale();
		return ResourceBundle.getBundle(filename, locale);
	}

	public String message(final String message) {
		final ResourceBundle bundle = this.getResourceBundle(FILE_NAME);
		try {
			return bundle.getString(message);
		} catch (MissingResourceException e) {
			return "?" + message + "?";
		}
	}

	public String message(final String message, final Object[] params) {
		Objects.requireNonNull(message);
		final ResourceBundle bundle = this.getResourceBundle(FILE_NAME);
		String text = bundle.getString(message);
		if (params != null) {
			final MessageFormat mf = new MessageFormat(text, this.getLocale());
			text = mf.format(params, new StringBuffer(), null).toString();
		}
		return text;
	}
}
