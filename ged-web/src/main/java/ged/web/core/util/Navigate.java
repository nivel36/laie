package ged.web.core.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;

import ged.web.core.Page;

public class Navigate {

	public static final String FACES_REDIRECT = "?faces-redirect=true";

	public static void toPage(final Page page) {
		final FacesContext fc = FacesContext.getCurrentInstance();
		final NavigationHandler nav = fc.getApplication().getNavigationHandler();
		nav.handleNavigation(fc, null, page.getUrl() + FACES_REDIRECT);
		fc.renderResponse();
	}

	public static void toPage(final String page) {
		final FacesContext fc = FacesContext.getCurrentInstance();
		final NavigationHandler nav = fc.getApplication().getNavigationHandler();
		nav.handleNavigation(fc, null, page + FACES_REDIRECT);
		fc.renderResponse();
	}

	private Navigate() {
	}

}
