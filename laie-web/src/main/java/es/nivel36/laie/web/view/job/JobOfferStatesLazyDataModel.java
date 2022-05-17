package es.nivel36.laie.web.view.job;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import es.nivel36.laie.ejb.job.offer.JobOfferService;
import es.nivel36.laie.ejb.job.offer.JobOfferStateEvent;

public class JobOfferStatesLazyDataModel extends LazyDataModel<JobOfferStateEvent> {
	
	@Inject
	private JobOfferService jobOfferService;
	
	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		return 0;
	}

	@Override
	public List<JobOfferStateEvent> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		return null;
	}

}
