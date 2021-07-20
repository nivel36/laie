package es.nivel36.laie;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;

import org.omnifaces.cdi.Eager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Eager
public class StartupConfig {

	private static final Logger logger = LoggerFactory.getLogger(StartupConfig.class);

	@PostConstruct
	public void startup() {
		logger.info("=====================================================================================");
		logger.info(",--.      ,---.   ,--. ,------.");
		logger.info("|  |     /  O  \\  |  | |  .---'");
		logger.info("|  |    |  .-.  | |  | |  `--,");
		logger.info("|  '--. |  | |  | |  | |  `---.");
		logger.info("`-----' `--' `--' `--' `------'");
		logger.info("");
		logger.info("LAIE - Versión 0.0.2-SNAPSHOT - (c) Nivel36");
		logger.info("=====================================================================================");
	}

}