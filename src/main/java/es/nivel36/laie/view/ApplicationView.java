package es.nivel36.laie.view;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import es.nivel36.laie.core.Language;
import es.nivel36.laie.core.view.AbstractView;

@ApplicationScoped
@Named
public class ApplicationView extends AbstractView {

	private static final long serialVersionUID = 1L;

	private String hostname;

	private List<Language> languages;

	@Inject
	@ConfigProperty(name = "version")
	private String version;

	public String getHostname() {
		return hostname;
	}

	public List<Language> getLanguages() {
		return languages;
	}

	public String getVersion() {
		return this.version;
	}

	@PostConstruct
	public void init() {
		this.hostname = getHostnameUrl();
		this.languages = Arrays.asList(Language.values());
	}

	private String getHostnameUrl() {
		final HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		final String url = request.getRequestURL().toString();
		final String uri = request.getRequestURI();
		final int hostnameLength = url.length() - uri.length();
		return url.substring(0, hostnameLength);
	}

	public void setVersion(final String version) {
		this.version = version;
	}
}
