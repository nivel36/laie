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

	public static Navigate to(final Page page) {
		return new Navigate(page);
	}

	private final Page page;

	private Map<String, Object> params;

	private Navigate(final Page page) {
		this.page = page;
	}

	private String buildQueryParams() {
		if (this.params != null && this.params.size() != 0) {
			final StringBuilder stringBuilder = new StringBuilder();
			final Set<Entry<String, Object>> entriesSet = this.params.entrySet();
			for (final Entry<String, Object> entry : entriesSet) {
				if (stringBuilder.length() == 0) {
					stringBuilder.append("?");
				} else {
					stringBuilder.append("&");
				}
				stringBuilder.append(entry.getKey()).append("=").append(entry.getValue().toString());
			}
			return stringBuilder.toString();
		} else {
			return null;
		}
	}

	public void doGet() {
		try {
			final FacesContext facesContext = FacesContext.getCurrentInstance();
			final ExternalContext externalContext = facesContext.getExternalContext();
			final String contextName = externalContext.getContextName();
			final StringBuilder url = new StringBuilder("/");
			url.append(contextName).append(this.page.url()).append(".xhtml").append(buildQueryParams());
			externalContext.redirect(url.toString());
		} catch (final IOException e) {
			throw new NavigationException("Unable to go to " + this.page, e);
		}
	}

	public void doPost() {
		final FacesContext fc = FacesContext.getCurrentInstance();
		final NavigationHandler nav = fc.getApplication().getNavigationHandler();
		nav.handleNavigation(fc, null, toUrl());
		fc.renderResponse();
	}

	public String toUrl() {
		String queryParams = buildQueryParams();
		if (queryParams == null) {
			queryParams = "?" + FACES_REDIRECT;
		} else {
			queryParams = queryParams + "&" + FACES_REDIRECT;
		}
		return this.page.url() + queryParams;
	}

	public Navigate withParams(final Map<String, Object> params) {
		this.params = params;
		return this;
	}
}