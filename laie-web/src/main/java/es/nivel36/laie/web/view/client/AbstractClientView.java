package es.nivel36.laie.web.view.client;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import org.omnifaces.cdi.Param;

import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.client.ClientService;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserService;
import es.nivel36.laie.web.core.view.AbstractView;

public class AbstractClientView extends AbstractView {

	private static final long serialVersionUID = -7192538193827969820L;

	protected @Param Client client;

	protected @Inject transient ClientService clientService;
	
	protected @Inject transient UserService userService;

	public List<User> queryOwner(final String query) {
		return this.userService.search(query, Page.FIRST_TEN_RESULTS).getResultData();
	}

	public Client getClient() {
		return this.client;
	}

	public void setClient(final Client client) {
		this.client = client;
	}

	public void setClientService(final ClientService clientService) {
		Objects.requireNonNull(clientService);
		this.clientService = clientService;
	}
	
	public void setUserService(final UserService userService) {
		Objects.requireNonNull(userService);
		this.userService = userService;
	}
}
