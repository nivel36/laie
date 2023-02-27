package es.nivel36.laie.web.view.job;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.job.candidature.JobCandidature;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureService;
import jakarta.inject.Inject;

public class JobCandidatureByCandidateLazyDataModel extends LazyDataModel<JobCandidature> {

	private static final long serialVersionUID = -7394237940138038448L;

	private Candidate candidate;

	private @Inject JobCandidatureService service;

	@Override
	public int count(final Map<String, FilterMeta> filterBy) {
		Objects.requireNonNull(candidate);
		return (int) service.countCandidatesJobCandidatures(candidate);
	}

	@Override
	public List<JobCandidature> load(final int first, final int pageSize, final Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		Objects.requireNonNull(candidate);
		return service.findCandidatesJobCandidatures(candidate, Page.of(first, pageSize));
	}

	public void setCandidate(final Candidate candidate) {
		this.candidate = candidate;
	}

	public void setService(final JobCandidatureService service) {
		Objects.requireNonNull(service);
		this.service = service;
	}

}
