package es.nivel36.laie.web.core.view;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.Language;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class ApplicationView implements Serializable {

	private static final long serialVersionUID = 44020436095563616L;
	private static final Logger log = LoggerFactory.getLogger(ApplicationView.class);

	private String hostname;
	private List<Language> languages;

	@PostConstruct
	public void init() {
		log.info("Application view init");
		this.languages = this.loadLanguages();
		this.hostname = this.loadHostname();
	}

	private List<Language> loadLanguages() {
		return Arrays.asList(Language.values());
	}

	private String loadHostname() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			log.error("Unable to check host name", e);
			return null;
		}
	}

	public String getHostname() {
		return hostname;
	}

	public List<Language> getLanguages() {
		return languages;
	}
}
