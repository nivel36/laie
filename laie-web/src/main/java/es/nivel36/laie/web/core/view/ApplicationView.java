package es.nivel36.laie.web.core.view;

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

@ApplicationScoped
@Named
public class ApplicationView extends AbstractView {

	private static final Logger log = LoggerFactory.getLogger(ApplicationView.class);

	private static final long serialVersionUID = 1L;

	private String hostname;

	private List<Language> languages;

	public List<Language> getLanguages() {
		return languages;
	}

	@PostConstruct
	public void init() {
		this.languages = Arrays.asList(Language.values());
		try {
			hostname = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			log.error("No se puede comprovar el nombre del host", e);
		}
	}

	public String getHostname() {
		return hostname;
	}
}
