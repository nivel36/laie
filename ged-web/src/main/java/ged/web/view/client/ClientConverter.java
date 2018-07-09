package ged.web.view.client;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import ged.ejb.client.Client;
import ged.ejb.client.ClientService;

@FacesConverter(managed = true, forClass = Client.class)
public class ClientConverter implements Converter<Client> {

	@Inject
	private ClientService clientService;

	@Override
	public Client getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if (value == null) {
			return null;
		}
		try {
			final long clientId = Long.parseLong(value);
			return this.clientService.find(clientId);
		}
		catch (final NumberFormatException e) {
			throw new ConverterException(value + " is not a valid id");
		}
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Client value) {
		if (value == null) {
			return null;
		}
		return String.valueOf(value.getId());
	}

	public void setClientService(final ClientService clientService) {
		this.clientService = clientService;
	}

}
