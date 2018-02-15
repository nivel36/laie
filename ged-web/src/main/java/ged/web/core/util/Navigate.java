package ged.web.core.util;

import java.io.IOException;

import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class Navigate {

	private static final String REDIRECT = "&faces-redirect=true";

	public static String candidateSearchUrl() {
		return Page.CandidateSearch.url();
	}

	public static String candidateUrl(final long id) {
		return Page.Candidate.url() + id + REDIRECT;
	}

	public static String clientSearchUrl() {
		return Page.ClientSearch.url();
	}

	public static String clientUrl(final long id) {
		return Page.Client.url() + id + REDIRECT;
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
		return Page.Index.url();
	}

	private static void post(final String page) {
		final FacesContext fc = FacesContext.getCurrentInstance();
		final NavigationHandler nav = fc.getApplication().getNavigationHandler();
		nav.handleNavigation(fc, null, page);
		fc.renderResponse();
	}

	public static void toCandidate(final long id) {
		get(candidateUrl(id));
	}

	public static void toCandidateSearch() {
		post(candidateSearchUrl());
	}

	public static void toClient(final long id) {
		get(Page.Client.url() + id);
	}

	public static void toClientSearch() {
		post(Page.ClientSearch.url());
	}

	public static void toJobOffer(final long id) {
		get(Page.JobOffer.url() + id);
	}

	public static void toJobOfferSearch() {
		post(Page.JobOfferSearch.url());
	}

	public static void toLogin() {
		post(Page.Login.url());
	}

	public static void toUser(final long id) {
		get(Page.User.url() + id);
	}

	public static void toUserSearch() {
		post(Page.UserSearch.url());
	}

	private Navigate() {
	}
}