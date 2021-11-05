package es.nivel36.laie.web.view.client;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import es.nivel36.laie.ejb.client.ClientDto;
import es.nivel36.laie.ejb.client.ClientService;

@FacesConverter(managed = true, forClass = ClientDto.class)
public class ClientConverter implements Converter<ClientDto> {

	@Inject
	private ClientService clientService;

	@Override
	public ClientDto getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if (value == null) {
			return null;
		}
		return this.clientService.findClientByUid(value);
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final ClientDto value) {
		if (value == null) {
			return null;
		}
		return value.getUid();
	}

	public void setClientService(final ClientService clientService) {
		this.clientService = clientService;
	}
}
