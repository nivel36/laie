package es.nivel36.laie;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.faces.annotation.FacesConfig;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.omnifaces.cdi.Eager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.core.Indexer;

@ApplicationScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Eager
public class StartupConfig {

	private static final Logger logger = LoggerFactory.getLogger(StartupConfig.class);

	@Inject
	private Indexer indexer;

	private void create(final String dir) throws IOException {
		final Path path = Paths.get(dir);
		if (Files.notExists(path)) {
			logger.info("Directory {} will be created.", dir);
			Files.createDirectories(path);
		}
	}

	private void createAppDirectories() throws IOException {
		this.create("/temp/lucene");
		this.create("/temp/files");
		this.create("/temp/img");
	}

	private void indexerInit() throws InterruptedException {
		try {
			this.indexer.index();
		} catch (final InterruptedException e) {
			logger.error("Indexer failed", e);
			Thread.currentThread().interrupt();
		}
	}

	public void init(@Observes @Initialized(ApplicationScoped.class) final ServletContext context)
			throws InterruptedException, IOException {
		logger.info("=====================================================================================");
		logger.info(",--.      ,---.   ,--. ,------.");
		logger.info("|  |     /  O  \\  |  | |  .---'");
		logger.info("|  |    |  .-.  | |  | |  `--,");
		logger.info("|  '--. |  | |  | |  | |  `---.");
		logger.info("`-----' `--' `--' `--' `------'");
		logger.info("");
		logger.info("LAIE - Versión 0.0.3 - (c) Nivel36");
		logger.info("=====================================================================================");
		this.createAppDirectories();
		this.indexerInit();
	}
}