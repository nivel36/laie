package es.nivel36.laie.web.view.job;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.client.ClientDto;
import es.nivel36.laie.ejb.job.offer.JobOfferDto;
import es.nivel36.laie.ejb.user.SimpleUserDto;
import es.nivel36.laie.ejb.user.UserDto;

@Named
@ViewScoped
public class AddJobView extends AbstractJobView {

	private static final long serialVersionUID = -1123122413333334446L;

	private static final Logger logger = LoggerFactory.getLogger(AddJobView.class);

	private ClientDto client;

	private SimpleUserDto owner;

	private boolean publish;

	@PostConstruct
	public void init() {
		logger.trace("New job offer init");
		this.jobOffer = new JobOfferDto();
		this.jobOffer.setOpenDate(LocalDate.now());
		final UserDto userDto = this.sessionUser.get();
		this.owner = new SimpleUserDto(userDto);
		this.fillRecruiters(userDto);
	}

	public void onClientSelect(final SelectEvent<ClientDto> event) {
		final ClientDto dto = event.getObject();
		if (dto != null) {
			this.client = dto;
		}
	}

	private void fillRecruiters(final UserDto user) {
		final List<UserDto> subordinateUsers = this.userService.findSubordinateUsers(user.getUid());
		for (final UserDto subordinate : subordinateUsers) {
			final SimpleUserDto simpleUserDto = new SimpleUserDto(subordinate);
			this.recruiters.add(simpleUserDto);
		}
	}

	public void save() {
		logger.debug("Create new client action performed");
		String[] recruitersUid = this.recruiters.stream().map(SimpleUserDto::getUid).toArray(String[]::new);
		this.jobOffer = this.jobOfferService.addJobOffer(client.getUid(), this.sessionUser.get().getUid(),
				recruitersUid, jobOffer);
		if (publish) {
			this.jobOfferService.publish(this.jobOffer.getUid());
		}
		Faces.redirect(ViewJobView.URL + "?job=" + this.jobOffer.getUid());
	}

	public ClientDto getClient() {
		return client;
	}

	public void setClient(final ClientDto client) {
		this.client = client;
	}

	public final SimpleUserDto getOwner() {
		return owner;
	}

	public final void setOwner(SimpleUserDto owner) {
		this.owner = owner;
	}

	public final boolean isPublish() {
		return publish;
	}

	public final void setPublish(boolean publish) {
		this.publish = publish;
	}
}