package ged.web.core.view;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import ged.ejb.core.Language;
import ged.web.core.util.WebConfigurationProperty;

@ApplicationScoped
@Named
public class ApplicationView extends AbstractView {

	private static final long serialVersionUID = 1L;

	@Inject
	@WebConfigurationProperty(value = "ged.buildtime")
	private String buildtime;

	private String hostname;

	private List<Language> languages;

	@Inject
	@WebConfigurationProperty(value = "ged.version")
	private String version;

	public String getBuildtime() {
		return this.buildtime;
	}

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

	public void setBuildtime(final String buildtime) {
		this.buildtime = buildtime;
	}

	public void setVersion(final String version) {
		this.version = version;
	}
}
