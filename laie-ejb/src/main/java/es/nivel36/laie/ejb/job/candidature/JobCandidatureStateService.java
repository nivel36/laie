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

	public void addJobCandidatureState(JobCandidatureState jobCandidatureState) {
		Objects.requireNonNull(jobCandidatureState);
		logger.debug("Add new client {}", jobCandidatureState);
		this.jobCandidatureStateDao.insert(jobCandidatureState);
	}

	public JobCandidatureState findInitialState() {
		return this.jobCandidatureStateDao.findInitialState();
	}

	public List<JobCandidatureState> findAll() {
		return jobCandidatureStateDao.findAll(JobCandidatureState.class, Page.ALL_RESULTS);
	}

	public JobCandidatureState findByName(String name) {
		return jobCandidatureStateDao.findByName(name);
	}

	public Transition createTransition(JobCandidatureState originState, JobCandidatureState destinationState,
			String event) {
		Objects.requireNonNull(originState);
		Objects.requireNonNull(destinationState);
		Objects.requireNonNull(event);

		Transition transition = new Transition();
		transition.setOriginState(originState);
		transition.setDestinationState(destinationState);
		transition.setEvent(event);

		transitionDao.insert(transition);
		return transition;
	}

	public List<JobCandidatureState> findNextStates(final JobCandidatureState state) {
		Objects.requireNonNull(state);
		return jobCandidatureStateDao.findNextStates(state);
	}

	public void transition(JobCandidatureState originState, JobCandidatureState destinationState) {
		stateEvent.fire(new StateEvent(originState, "BEFORE_TRANSITION"));

		boolean validTransition = originState.getOriginTransitions().stream()
				.anyMatch(t -> t.getDestinationState().equals(destinationState));

		if (!validTransition) {
			throw new IllegalStateException("Invalid transition");
		}

		stateEvent.fire(new StateEvent(destinationState, "AFTER_TRANSITION"));
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
