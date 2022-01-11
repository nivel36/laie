package es.nivel36.laie.api.v1.candidate;

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
import es.nivel36.laie.ejb.candidate.CandidateDto;
import es.nivel36.laie.ejb.candidate.CandidateService;
import es.nivel36.laie.ejb.user.UserService;

@Path("candidate")
@ApplicationScoped
public class CandidateRestController extends AbstractRestController {

	@Inject
	private CandidateService candidateService;

	@Inject
	UserService userSerivce;

	@GET
	@Path("/{uid:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public CandidateDto find(@PathParam("uid") final String uid) {
		return this.candidateService.findCandidateByUid(uid);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insert(@Valid final CandidateDto candidateDto, final String ownerUid) {
		Response.ResponseBuilder builder = null;
		this.candidateService.addCandidate(candidateDto, ownerUid);
		builder = Response.ok();
		return builder.build();
	}

	public void setCandidateService(final CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public void setUserSerivce(final UserService userSerivce) {
		this.userSerivce = userSerivce;
	}
}