package es.nivel36.laie.web.view.job;


import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.job.offer.JobOfferService;
import es.nivel36.laie.ejb.user.User;
import jakarta.inject.Inject;

public class JobOffersByOwnerOrRecruiterLazyDataModel extends LazyDataModel<JobOffer> {

	private static final long serialVersionUID = 6084482828895151751L;

	private transient @Inject JobOfferService jobOfferService;

	private User user;

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		return (int) jobOfferService.countJobOffersByOwnerOrRecruiter(this.user);
	}

	@Override
	public List<JobOffer> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
		return jobOfferService.findJobOffersByOwnerOrRecruiter(this.user, Page.of(first, pageSize));
	}

	public void setUser(final User user) {
		Objects.requireNonNull(user);
		this.user = user;
	}
}