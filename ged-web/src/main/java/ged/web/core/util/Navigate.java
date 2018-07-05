package ged.web.core.util;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class Navigate {

	public static final String FACES_REDIRECT = "faces-redirect=true";

	public static Navigate to(final PageEnum page) {
		return new Navigate(page.url());
	}

	public static Navigate to(final String url) {
		return new Navigate(url);
	}

	private Map<String, Object> params;

	private final String url;

	private Navigate(final String url) {
		this.url = url;
	}

	private String buildQueryParams() {
		if ((this.params != null) && (this.params.size() != 0)) {
			final StringBuilder stringBuilder = new StringBuilder();
			final Set<Entry<String, Object>> entriesSet = this.params.entrySet();
			for (final Entry<String, Object> entry : entriesSet) {
				if (stringBuilder.length() == 0) {
					stringBuilder.append("?");
				}
				else {
					stringBuilder.append("&");
				}
				stringBuilder.append(entry.getKey()).append("=").append(entry.getValue().toString());
			}
			return stringBuilder.toString();
		}
		else {
			return null;
		}
	}

	public void doGet() {
		try {
			final FacesContext facesContext = FacesContext.getCurrentInstance();
			final ExternalContext externalContext = facesContext.getExternalContext();
			final String contextName = externalContext.getContextName();
			final StringBuilder url = new StringBuilder("/");
			url.append(contextName).append(this.url).append(".xhtml").append(this.buildQueryParams());
			externalContext.redirect(url.toString());
		}
		catch (final IOException e) {
			throw new NavigationException("Unable to go to " + this.url, e);
		}
	}

	public void doPost() {
		final FacesContext fc = FacesContext.getCurrentInstance();
		final NavigationHandler nav = fc.getApplication().getNavigationHandler();
		if ((this.params != null) && (this.params.size() > 0)) {
			fc.getExternalContext().getFlash().putAll(this.params);
		}
		nav.handleNavigation(fc, null, this.url + "?" + FACES_REDIRECT);
		fc.renderResponse();
	}

	public Navigate withParams(final Map<String, Object> params) {
		this.params = params;
		return this;
	}
}