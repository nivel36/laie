package es.nivel36.laie.ejb.job.submission;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.ObservesAsync;

@ApplicationScoped
public class StateEventListener {

	private static final Logger logger = LoggerFactory.getLogger(StateEventListener.class);

	public void onBeforeTransition(@ObservesAsync StateEvent event) {
		if ("BEFORE_TRANSITION".equals(event.getEventType())) {
			logger.debug("Event fired before transition: State ID {}", event.getState());
		}
	}

	public void onAfterTransition(@ObservesAsync StateEvent event) {
		if ("AFTER_TRANSITION".equals(event.getEventType())) {
			logger.debug("Event fired after transition: State ID {}", event.getState());
		}
	}
}