package es.nivel36.laie.web.view.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.nivel36.laie.ejb.client.ClientDto;
import es.nivel36.laie.ejb.client.ClientService;
import es.nivel36.core.model.Page;
import es.nivel36.laie.ejb.job.offer.JobOfferDto;
import es.nivel36.laie.ejb.job.offer.JobOfferService;
import es.nivel36.laie.ejb.user.SimpleUserDto;
import es.nivel36.laie.ejb.user.UserDto;
import es.nivel36.laie.ejb.user.UserService;
import es.nivel36.laie.web.core.view.AbstractView;

public abstract class AbstractJobView extends AbstractView {

	private static final long serialVersionUID = 3680467978756165892L;

	protected JobOfferDto jobOffer;

	protected transient List<SimpleUserDto> recruiters = new ArrayList<>();

	@Inject
	protected transient ClientService clientService;

	@Inject
	protected transient JobOfferService jobOfferService;

	@Inject
	protected transient UserService userService;

	public List<ClientDto> completeClient(final String query) {
		return this.clientService.search(query, Page.TEN_RESULTS_PER_PAGE).getResultData();
	}

	public void onOwnerSelect(final SelectEvent<SimpleUserDto> event) {
		final SimpleUserDto user = event.getObject();
		if (user != null) {
			this.jobOfferService.changeJobOffersOwner(this.jobOffer.getUid(), user.getUid());
		}
	}

	public List<SimpleUserDto> queryOwner(final String query) {
		final List<UserDto> resultData = this.userService.search(query, Page.TEN_RESULTS_PER_PAGE).getResultData();
		final List<SimpleUserDto> users = resultData.stream().map(SimpleUserDto::new).collect(Collectors.toList());
		return users;
	}

	public List<SimpleUserDto> queryRecruiter(final String query) {
		final List<SimpleUserDto> recruiters = new ArrayList<SimpleUserDto>();
		final List<UserDto> searchRecruiters = this.userService.search(query, Page.ALL_RESULTS).getResultData();
		for(final UserDto searchRecruiter:searchRecruiters) {
			final SimpleUserDto recruiter = new SimpleUserDto(searchRecruiter);
			this.recruiters.add(recruiter);
		}
		return recruiters;
	}

	public JobOfferDto getJobOffer() {
		return this.jobOffer;
	}

	public List<SimpleUserDto> getRecruiters() {
		return this.recruiters;
	}

	public void setJobOffer(final JobOfferDto jobOffer) {
		this.jobOffer = jobOffer;
	}

	public void setRecruiters(final List<SimpleUserDto> recruiters) {
		this.recruiters = recruiters;
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		Objects.requireNonNull(jobOfferService);
		this.jobOfferService = jobOfferService;
	}

	public void setUserService(final UserService userService) {
		Objects.requireNonNull(userService);
		this.userService = userService;
	}
}
