package es.nivel36.laie.web.core;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.faces.annotation.FacesConfig;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.model.Indexer;

@ApplicationScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
public class StartupConfig {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

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
		logger.info("Setting up application");
		this.createAppDirectories();
		this.indexerInit();
	}
}