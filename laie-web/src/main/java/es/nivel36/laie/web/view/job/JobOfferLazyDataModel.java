package es.nivel36.laie.web.view.job;


import org.hibernate.search.engine.search.query.SearchResult;

import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.SortField;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.job.offer.JobOfferService;
import es.nivel36.laie.web.core.view.AbstractLazyDataModel;
import jakarta.inject.Inject;

public class JobOfferLazyDataModel extends AbstractLazyDataModel<JobOffer> {

	private static final long serialVersionUID = 6084482828895151751L;

	@Inject
	private transient JobOfferService jobOfferService;

	@Override
	protected SearchResult<JobOffer> search(String searchText, Page page, SortField sortField,
			String[] searchFilter) {
		return jobOfferService.search(searchText, page, sortField, searchFilter);
	}

	@Override
	protected JobOffer find(Long id) {
		return jobOfferService.findJobOfferById(id);
	}
}