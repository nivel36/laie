package es.nivel36.laie.web.view.candidate;

import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.candidate.CandidateService;
import es.nivel36.laie.ejb.candidate.CandidateFileAttachment;
import es.nivel36.laie.ejb.core.model.Page;
import jakarta.inject.Inject;

public class FilesByCandidateLazyDataModel extends LazyDataModel<CandidateFileAttachment> {

	private static final long serialVersionUID = -4871133088132391207L;

	private transient @Inject CandidateService candidateService;
	private Candidate candidate;

	public void setCandidate(final Candidate candidate) {
		this.candidate = candidate;
	}

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		return (int) this.candidateService.countCandidateFiles(candidate);
	}

	@Override
	public List<CandidateFileAttachment> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		return candidateService.findCandidateFiles(candidate, Page.of(first, pageSize));
	}

}