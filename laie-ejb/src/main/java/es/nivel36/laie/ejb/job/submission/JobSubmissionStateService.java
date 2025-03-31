package es.nivel36.laie.ejb.job.submission;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.model.Page;
import jakarta.ejb.Stateless;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@Stateless
public class JobSubmissionStateService {

	private static final Logger logger = LoggerFactory.getLogger(JobSubmissionStateService.class);

	private @Inject JobSubmissionStateDao jobSubmissionStateDao;
	private @Inject TransitionDao transitionDao;
	private @Inject Event<StateEvent> stateEvent;

	public void addJobSubmissionState(final JobSubmissionState jobSubmissionState) {
		Objects.requireNonNull(jobSubmissionState);
		logger.debug("Adding new job submission state {}", jobSubmissionState);
		this.jobSubmissionStateDao.insert(jobSubmissionState);
	}

	public JobSubmissionState findInitialState() {
		logger.debug("Retrieving job submission initial state");
		return this.jobSubmissionStateDao.findInitialState();
	}

	public List<JobSubmissionState> findAll() {
		logger.debug("Retrieving all job submission states");
		return jobSubmissionStateDao.findAll(JobSubmissionState.class, Page.ALL_RESULTS);
	}

	public JobSubmissionState findByName(final String name) {
		Objects.requireNonNull(name);
		logger.debug("Retrieving job submission state by name {}", name);
		return jobSubmissionStateDao.findByName(name);
	}

	public Transition createTransition(final JobSubmissionState originState, final JobSubmissionState destinationState,
			final String event) {
		Objects.requireNonNull(originState);
		Objects.requireNonNull(destinationState);
		Objects.requireNonNull(event);
		logger.debug("Creating transition {} betwen {} and {}", event, originState, destinationState);
		final Transition transition = new Transition(event, originState, destinationState);
		transitionDao.insert(transition);
		return transition;
	}

	public List<JobSubmissionState> findNextStates(final JobSubmissionState state) {
		Objects.requireNonNull(state);
		logger.debug("Retrieving next job jobSubmission states of state {}", state);
		return jobSubmissionStateDao.findNextStates(state);
	}

	public void transition(final JobSubmissionState originState, final JobSubmissionState destinationState) {
		Objects.requireNonNull(originState);
		Objects.requireNonNull(destinationState);
		logger.debug("Transition betwen {} and {}", originState, destinationState);
		final StateEvent eventBeforeTransition = new StateEvent(originState, "BEFORE_TRANSITION");
		stateEvent.fire(eventBeforeTransition);

		final boolean validTransition = this.jobSubmissionStateDao.findNextStates(originState).stream()
				.anyMatch(s -> s.equals(destinationState));

		if (!validTransition) {
			throw new IllegalStateException("Invalid transition");
		}

		final StateEvent eventAfterTransition = new StateEvent(destinationState, "AFTER_TRANSITION");
		stateEvent.fire(eventAfterTransition);
	}

	public void setJobSubmissionStateDao(final JobSubmissionStateDao jobSubmissionStateDao) {
		this.jobSubmissionStateDao = Objects.requireNonNull(jobSubmissionStateDao);
	}

	public void setTransitionDao(final TransitionDao transitionDao) {
		this.transitionDao = Objects.requireNonNull(transitionDao);
	}

	public void setStateEvent(final Event<StateEvent> stateEvent) {
		this.stateEvent = Objects.requireNonNull(stateEvent);
	}
}
