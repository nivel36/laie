package es.nivel36.laie.api.v1.client;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.nivel36.laie.api.v1.AbstractRestController;
import es.nivel36.laie.ejb.client.ClientDto;
import es.nivel36.laie.ejb.client.ClientService;

@Path("client")
@ApplicationScoped
public class ClientRestController extends AbstractRestController {

	@Inject
	private ClientService clientService;

	@GET
	@Path("/client/{uid}")
	@Produces(MediaType.APPLICATION_JSON)
	public ClientDto findByCif(@PathParam("uid") final String uid) {
		return this.clientService.findClientByUid(uid);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newClient(@Valid final ClientDto client, final String ownerUid) {
		this.clientService.addClient(client, ownerUid);
		final Response.ResponseBuilder builder = Response.ok();
		return builder.build();
	}
}
