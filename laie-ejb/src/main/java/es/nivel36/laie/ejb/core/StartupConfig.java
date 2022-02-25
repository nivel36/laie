package es.nivel36.laie.ejb.core;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.model.Indexer;
import es.nivel36.laie.ejb.core.util.ConfigurationProperty;

@Singleton
@Startup
public class StartupConfig {

	private static final Logger logger = LoggerFactory.getLogger(StartupConfig.class);

	@Inject
	private Indexer indexer;

	@Inject
	@ConfigurationProperty(value = "file.directory")
	private String fileDirectory;

	@Inject
	@ConfigurationProperty(value = "image.directory")
	private String imageDirectory;

	@Inject
	@ConfigurationProperty(value = "lucene.directory")
	private String luceneDirectory;

	@PostConstruct
	public void init() {
		logger.info("Setting up application");
		this.createAppDirectories();
		this.indexerInit();
	}

	private void createAppDirectories() {
		this.create(luceneDirectory);
		this.create(fileDirectory);
		this.create(imageDirectory);
	}

	private void create(final String dir) {
		final Path path = Paths.get(dir);
		if (Files.notExists(path)) {
			try {
				logger.info("Directory {} will be created.", dir);
				Files.createDirectories(path);
			} catch (final IOException e) {
				throw new UncheckedIOException(e);
			}
		}
	}

	private void indexerInit() {
		try {
			this.indexer.index();
		} catch (final InterruptedException e) {
			logger.error("Indexer failed", e);
			Thread.currentThread().interrupt();
		}
	}

	public void setIndexer(final Indexer indexer) {
		Objects.requireNonNull(indexer);
		this.indexer = indexer;
	}
	
	public void setFileDirectory(final String fileDirectory) {
		Objects.requireNonNull(fileDirectory);
		this.fileDirectory = fileDirectory;
	}

	public void setImageDirectory(final String imageDirectory) {
		Objects.requireNonNull(imageDirectory);
		this.imageDirectory = imageDirectory;
	}

	public void setLuceneDirectory(final String luceneDirectory) {
		Objects.requireNonNull(luceneDirectory);
		this.luceneDirectory = luceneDirectory;
	}
}
