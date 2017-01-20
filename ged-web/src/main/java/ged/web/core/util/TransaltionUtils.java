package ged.web.core.util;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

public class TransaltionUtils {

	private static final String FILE_NAME = "ged.i18n";

	private static ResourceBundle getResourceBundle(final String filename) {
		final Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		return ResourceBundle.getBundle(filename, locale);
	}

	public static String translate(final String message) {
		final ResourceBundle bundle = getResourceBundle(FILE_NAME);
		return bundle.getString(message);
	}

	private TransaltionUtils() {
	}
}