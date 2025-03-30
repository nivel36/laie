package es.nivel36.laie.ejb.job.offer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JobOfferStateService {

	private Map<JobOfferState, List<JobOfferState>> nextStates;

	@PostConstruct
	public void init() {
		nextStates = new HashMap<>(Map.of(JobOfferState.OPENED,
				List.of(JobOfferState.CLOSED, JobOfferState.FINISHED, JobOfferState.PAUSED), JobOfferState.CREATED,
				List.of(JobOfferState.CLOSED, JobOfferState.FINISHED, JobOfferState.PAUSED, JobOfferState.OPENED),
				JobOfferState.CLOSED, List.of(JobOfferState.OPENED), JobOfferState.FINISHED,
				List.of(JobOfferState.OPENED), JobOfferState.PAUSED,
				List.of(JobOfferState.CLOSED, JobOfferState.FINISHED, JobOfferState.OPENED)));
	}

	public List<JobOfferState> findNextStates(final JobOfferState jobOfferState) {
		Objects.requireNonNull(jobOfferState);
		return nextStates.get(jobOfferState);
	}

	public List<JobOfferState> findAll() {
		return Arrays.asList(JobOfferState.values());
	}
}
