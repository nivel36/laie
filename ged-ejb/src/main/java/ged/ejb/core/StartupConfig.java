package ged.ejb.core;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import ged.ejb.core.model.Indexer;

@ApplicationScoped
public class StartupConfig {

	private static final Logger logger = Logger.getLogger(StartupConfig.class.getName());

	@Inject
	private Indexer indexer;

	public void init(@Observes @Initialized(ApplicationScoped.class) final ServletContext context)
			throws InterruptedException {
		StartupConfig.logger.log(Level.INFO, "Setting up application");
		try {
			this.indexer.index();
		} catch (final InterruptedException e) {
			StartupConfig.logger.log(Level.SEVERE, "Indexer fail", e);
			throw e;
		}
	}
}