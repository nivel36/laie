package es.nivel36.laie.api.v1.candidate;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.nivel36.laie.api.v1.AbstractRestController;
import es.nivel36.laie.api.v1.mapper.Mapper;
import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.candidate.CandidateService;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.user.UserService;

@Path("candidate")
@ApplicationScoped
public class CandidateRestController extends AbstractRestController {

	@Inject
	@Mapper
	private CandidateMapper candidateMapper;

	@Inject
	private CandidateService candidateService;

	@Inject
	UserService userSerivce;

	private List<CandidateDto> convertToCandidateDtoList(final List<Candidate> candidates) {
		final List<CandidateDto> candidateDtos = new ArrayList<>();
		for (final Candidate candidate : candidates) {
			final CandidateDto candidateDto = this.candidateMapper.mapEntity(candidate);
			candidateDtos.add(candidateDto);
		}
		return candidateDtos;
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public CandidateDto find(@PathParam("id") final long id) {
		final Candidate candidate = this.candidateService.find(id);
		return this.candidateMapper.mapEntity(candidate);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<CandidateDto> findAll() {
		final List<Candidate> candidates = this.candidateService.findAll(Page.ALL_RESULTS);
		return this.convertToCandidateDtoList(candidates);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insert(@Valid final CandidateDto candidateDto) {
		Response.ResponseBuilder builder = null;
		final Candidate candidateToInsert = this.candidateMapper.mapDto(candidateDto);
		this.candidateService.save(candidateToInsert);
		builder = Response.ok();
		return builder.build();
	}

	@GET
	@Path("/search/{searchText}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CandidateDto> search(@PathParam("searchText") final String searchText) {
		final List<Candidate> candidates = this.candidateService.search(searchText, Page.ALL_RESULTS).getResultData();
		return this.convertToCandidateDtoList(candidates);
	}

	public void setCandidateService(final CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public void setUserSerivce(final UserService userSerivce) {
		this.userSerivce = userSerivce;
	}
}