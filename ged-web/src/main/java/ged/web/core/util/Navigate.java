package ged.web.core.util;

import java.io.IOException;

import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class Navigate {

	private static final String REDIRECT = "&faces-redirect=true";

	public static String candidateSearchUrl() {
		return Page.CANDIDATE_SEARCH.url();
	}

	public static String candidateUrl(final long id) {
		return Page.CANDIDATE.url() + id + REDIRECT;
	}

	public static String clientSearchUrl() {
		return Page.CLIENT_SEARCH.url();
	}

	public static String clientUrl(final long id) {
		return Page.CLIENT.url() + id + REDIRECT;
	}

	private static void get(final String page) {
		try {
			final FacesContext facesContext = FacesContext.getCurrentInstance();
			final ExternalContext externalContext = facesContext.getExternalContext();
			final String contextName = externalContext.getContextName();
			final StringBuilder url = new StringBuilder("/");
			url.append(contextName).append(page);
			externalContext.redirect(url.toString());
		} catch (final IOException e) {
			throw new NavigationException("Unable to go to " + page, e);
		}
	}

	public static String indexUrl() {
		return Page.INDEX.url();
	}

	public static String jobOfferSearchUrl() {
		return Page.JOB_OFFER_SEARCH.url();
	}

	public static String maintenancesUrl() {
		return Page.MAINTENANCE.url();
	}

	private static void post(final String page) {
		final FacesContext fc = FacesContext.getCurrentInstance();
		final NavigationHandler nav = fc.getApplication().getNavigationHandler();
		nav.handleNavigation(fc, null, page);
		fc.renderResponse();
	}

	public static String reportsSearchUrl() {
		return Page.REPORT.url();
	}

	public static void toCandidate(final long id) {
		get(candidateUrl(id));
	}

	public static void toCandidateSearch() {
		post(candidateSearchUrl());
	}

	public static void toClient(final long id) {
		get(Page.CLIENT.url() + id);
	}

	public static void toClientSearch() {
		post(Page.CLIENT_SEARCH.url());
	}

	public static void toJobOffer(final long id) {
		get(Page.JOB_OFFER.url() + id);
	}

	public static void toJobOfferSearch() {
		post(Page.JOB_OFFER_SEARCH.url());
	}

	public static void toLogin() {
		post(Page.LOGIN.url());
	}

	public static void toUser(final long id) {
		get(Page.USER.url() + id);
	}

	public static void toUserSearch() {
		post(Page.USER_SEARCH.url());
	}

	public static String userSearchUrl() {
		return Page.USER_SEARCH.url();
	}

	private Navigate() {
	}
}