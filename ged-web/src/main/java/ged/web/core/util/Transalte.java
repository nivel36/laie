package ged.web.core.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

public class Transalte {

	private static final String FILE_NAME = "ged.i18n";

	private static Locale getLocale() {
		final UIViewRoot uIViewRoot = FacesContext.getCurrentInstance().getViewRoot();
		final Locale locale;
		if (uIViewRoot != null) {
			locale = uIViewRoot.getLocale();
		} else {
			locale = Locale.ENGLISH;
		}
		return locale;
	}

	private static ResourceBundle getResourceBundle(final String filename) {
		Locale locale = getLocale();
		return ResourceBundle.getBundle(filename, locale);
	}

	public static String message(final String message) {
		final ResourceBundle bundle = getResourceBundle(FILE_NAME);
		return bundle.getString(message);
	}

	public static String message(final String message, Object[] params) {
		final ResourceBundle bundle = getResourceBundle(FILE_NAME);
		String text = bundle.getString(message);
		if (params != null) {
			MessageFormat mf = new MessageFormat(text, getLocale());
			text = mf.format(params, new StringBuffer(), null).toString();
		}
		return text;
	}

	private Transalte() {
	}
}