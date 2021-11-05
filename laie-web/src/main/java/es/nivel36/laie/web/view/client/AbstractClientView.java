package es.nivel36.laie.web.view.client;

import java.util.Objects;

import javax.inject.Inject;

import es.nivel36.laie.ejb.client.ClientDto;
import es.nivel36.laie.ejb.client.ClientService;
import es.nivel36.laie.web.core.util.PageEnum;
import es.nivel36.laie.web.core.view.AbstractView;

public abstract class AbstractClientView extends AbstractView {

	private static final long serialVersionUID = 1L;

	protected ClientDto client;

	@Inject
	protected transient ClientService clientService;

	protected String clientUrl() {
		return this.navigator.getRedirectUrl(PageEnum.CLIENT, this.client.getUid());
	}

	public ClientDto getClient() {
		return this.client;
	}

	public void setClientService(final ClientService clientService) {
		Objects.requireNonNull(clientService);
		this.clientService = clientService;
	}
}
