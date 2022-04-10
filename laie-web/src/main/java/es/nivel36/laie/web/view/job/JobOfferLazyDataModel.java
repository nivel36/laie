package es.nivel36.laie.web.view.job;

import java.util.Map;
import java.util.Objects;

import org.primefaces.model.FilterMeta;

import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.search.SearchFacets;
import es.nivel36.laie.ejb.core.model.search.SearchResult;
import es.nivel36.laie.ejb.core.model.search.SortField;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.job.offer.JobOfferService;
import es.nivel36.laie.web.core.view.AbstractLazyDataModel;

public class JobOfferLazyDataModel extends AbstractLazyDataModel<JobOffer> {

	private static final long serialVersionUID = 6084482828895151751L;

	private transient JobOfferService jobOfferService;

	public JobOfferLazyDataModel(final JobOfferService jobOfferService) {
		Objects.requireNonNull(jobOfferService, "JobOfferService can't be null");
		this.jobOfferService = jobOfferService;
	}

	@Override
	protected SearchResult<JobOffer> search(String searchText, Page page, SortField sortField,
			SearchFacets searchFilter) {
		return jobOfferService.search(searchText, page, sortField, searchFilter);
	}

	@Override
	protected JobOffer find(String rowkey) {
		return jobOfferService.findJobOfferById(rowkey);
	}

	@Override
	protected String getKey(JobOffer entity) {
		return entity.getId();
	}

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		return 0;
	}
}