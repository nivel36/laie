package ged.web.core;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.model.Indexer;

@ApplicationScoped
public class StartupConfig {

	private static final Logger logger = LoggerFactory.getLogger(StartupConfig.class.getName());

	@Inject
	private Indexer indexer;

	public void init(@Observes @Initialized(ApplicationScoped.class) final ServletContext context)
			throws InterruptedException {
		logger.info("Setting up application");
		try {
			this.indexer.index();
		} catch (final InterruptedException e) {
			StartupConfig.logger.error("Indexer fail", e);
			throw e;
		}
	}
}