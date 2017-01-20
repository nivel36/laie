package ged.web.core.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;

public class NavigationUtils {

	public static void gotoPage(final String page) {
		final FacesContext fc = FacesContext.getCurrentInstance();
		final NavigationHandler nav = fc.getApplication().getNavigationHandler();
		nav.handleNavigation(fc, null, page + "?faces-redirect=true");
		fc.renderResponse();
	}

	private NavigationUtils() {
	}

}
