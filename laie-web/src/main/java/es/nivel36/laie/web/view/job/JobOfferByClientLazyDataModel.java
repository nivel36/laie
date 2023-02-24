package es.nivel36.laie.web.view.job;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.job.offer.JobOfferService;
import jakarta.inject.Inject;

public class JobOfferByClientLazyDataModel extends LazyDataModel<JobOffer> {

	private static final long serialVersionUID = 7812877254463971437L;

	private Client client;

	private @Inject JobOfferService jobOfferService;

	public void setClient(Client client) {
		this.client = client;
	}

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		Objects.requireNonNull(client);
		return (int) jobOfferService.countJobOffersByClient(client);
	}

	@Override
	public List<JobOffer> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		Objects.requireNonNull(client);
		return jobOfferService.findJobOffersByClient(client, Page.of(first, pageSize));
	}

	public void setJobOfferService(JobOfferService jobOfferService) {
		Objects.requireNonNull(jobOfferService);
		this.jobOfferService = jobOfferService;
	}

}
