package es.nivel36.laie.ejb.job.candidature;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.model.Page;
import jakarta.ejb.Stateless;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@Stateless
public class JobCandidatureStateService {

	private static final Logger logger = LoggerFactory.getLogger(JobCandidatureStateService.class);

	private @Inject JobCandidatureStateDao jobCandidatureStateDao;
	private @Inject TransitionDao transitionDao;
	private @Inject Event<StateEvent> stateEvent;

	public void addJobCandidatureState(final JobCandidatureState jobCandidatureState) {
		Objects.requireNonNull(jobCandidatureState);
		logger.debug("Add new job candidature state {}", jobCandidatureState);
		this.jobCandidatureStateDao.insert(jobCandidatureState);
	}

	public JobCandidatureState findInitialState() {
		logger.debug("Find job candidature initial state");
		return this.jobCandidatureStateDao.findInitialState();
	}

	public List<JobCandidatureState> findAll() {
		logger.debug("Find all job candidature states");
		return jobCandidatureStateDao.findAll(JobCandidatureState.class, Page.ALL_RESULTS);
	}

	public JobCandidatureState findByName(final String name) {
		Objects.requireNonNull(name);
		logger.debug("Find all job candidature states");
		return jobCandidatureStateDao.findByName(name);
	}

	public Transition createTransition(final JobCandidatureState originState,
			final JobCandidatureState destinationState, final String event) {
		Objects.requireNonNull(originState);
		Objects.requireNonNull(destinationState);
		Objects.requireNonNull(event);

		logger.debug("Create transition {}", event);
		final Transition transition = new Transition();
		transition.setOriginState(originState);
		transition.setDestinationState(destinationState);
		transition.setEvent(event);

		transitionDao.insert(transition);
		return transition;
	}

	public List<JobCandidatureState> findNextStates(final JobCandidatureState state) {
		Objects.requireNonNull(state);
		logger.debug("Find next job candidature states of state {}", state);
		return jobCandidatureStateDao.findNextStates(state);
	}

	public void transition(final JobCandidatureState originState, final JobCandidatureState destinationState) {
		Objects.requireNonNull(originState);
		Objects.requireNonNull(destinationState);
		logger.debug("Transition betwen {} and {}", originState, destinationState);
		final StateEvent eventBeforeTransition = new StateEvent(originState, "BEFORE_TRANSITION");
		stateEvent.fire(eventBeforeTransition);

		final boolean validTransition = this.jobCandidatureStateDao.findNextStates(originState).stream()
				.anyMatch(s -> s.equals(destinationState));

		if (!validTransition) {
			throw new IllegalStateException("Invalid transition");
		}

		final StateEvent eventAfterTransition = new StateEvent(destinationState, "AFTER_TRANSITION");
		stateEvent.fire(eventAfterTransition);
	}

	public void setJobCandidatureStateDao(final JobCandidatureStateDao jobCandidatureStateDao) {
		Objects.requireNonNull(jobCandidatureStateDao);
		this.jobCandidatureStateDao = jobCandidatureStateDao;
	}

	public void setTransitionDao(final TransitionDao transitionDao) {
		Objects.requireNonNull(transitionDao);
		this.transitionDao = transitionDao;
	}

	public void setStateEvent(final Event<StateEvent> stateEvent) {
		Objects.requireNonNull(stateEvent);
		this.stateEvent = stateEvent;
	}
}
