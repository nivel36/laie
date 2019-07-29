package ged.api.v1.client;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ged.api.v1.AbstractRestController;
import ged.ejb.client.Client;
import ged.ejb.client.ClientService;
import ged.ejb.core.model.Page;
import io.swagger.annotations.Api;

@Path("client")
@Api("client")
@ApplicationScoped
public class ClientRestController extends AbstractRestController {

	@Inject
	private ClientMapper clientMapper;

	@Inject
	private ClientService clientService;

	private List<ClientDto> convertToClientDtoList(final List<Client> clients) {
		final List<ClientDto> clientDtos = new ArrayList<>();
		for (final Client client : clients) {
			final ClientDto clientDto = this.clientMapper.mapEntity(client);
			clientDtos.add(clientDto);
		}
		return clientDtos;
	}

	@GET
	@Path("/client/{cif}")
	@Produces(MediaType.APPLICATION_JSON)
	public ClientDto findByCif(@PathParam("cif") final String cif) {
		final Client client = this.clientService.findClientByCif(cif);
		return this.clientMapper.mapEntity(client);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newClient(@Valid final ClientDto client) {
		Response.ResponseBuilder builder = null;
		final Client clientToInsert = this.clientMapper.mapDto(client);
		this.clientService.save(clientToInsert);
		builder = Response.ok();
		return builder.build();
	}

	@GET
	@Path("/search/{searchText}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ClientDto> search(@PathParam("searchText") final String searchText, @QueryParam("offset") final int offset,
			@QueryParam("limit") final int limit) {
		final List<Client> clients = this.clientService.search(searchText, new Page(offset, limit)).getResultData();
		return this.convertToClientDtoList(clients);
	}
}
