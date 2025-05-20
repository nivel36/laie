package es.nivel36.laie.web.view.job;

import java.util.Objects;

import org.hibernate.search.engine.search.query.SearchResult;

import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.SortField;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.job.offer.JobOfferService;
import es.nivel36.laie.web.core.view.AbstractLazyDataModel;
import jakarta.inject.Inject;

public class JobOfferLazyDataModel extends AbstractLazyDataModel<JobOffer> {

	private static final long serialVersionUID = 6084482828895151751L;

	private transient @Inject JobOfferService jobOfferService;

	@Override
	protected SearchResult<JobOffer> search(final String searchText, final Page page, final SortField sortField,
			final String[] searchFilter) {
		return jobOfferService.search(searchText, page, sortField, searchFilter);
	}

	@Override
	protected JobOffer find(final Long id) {
		Objects.requireNonNull(id);
		return jobOfferService.findJobOfferById(id);
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		this.jobOfferService = Objects.requireNonNull(jobOfferService);
	}
}