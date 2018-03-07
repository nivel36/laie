package ged.web.view.client;

import java.util.Objects;

import ged.ejb.client.Client;
import ged.web.core.ActionCallback;

public class SelectClientAction implements ActionCallback<Client> {

	private final ClientSelecteable parent;

	public SelectClientAction(final ClientSelecteable parent) {
		Objects.requireNonNull(parent);
		this.parent = parent;
	}

	@Override
	public void doAction(final Client selectedClient) {
		this.parent.onClientSelect(selectedClient);
	}
}