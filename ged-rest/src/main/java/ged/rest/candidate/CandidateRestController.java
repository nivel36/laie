package ged.rest.candidate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.core.Address;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.rest.AbstractRestController;

@Path("candidates")
@ApplicationScoped
public class CandidateRestController extends AbstractRestController {

	@Inject
	private CandidateService candidateService;

	@Inject
	UserService userSerivce;

	private List<CandidateDto> convertToCandidateDtoList(final List<Candidate> candidates) {
		final List<CandidateDto> candidateDtos = new ArrayList<>();
		for (final Candidate candidate : candidates) {
			final CandidateDto candidateDto = new CandidateDto(candidate);
			candidateDtos.add(candidateDto);
		}
		return candidateDtos;
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public CandidateDto find(@PathParam("id") final long id) {
		final Candidate candidate = this.candidateService.find(id);
		final CandidateDto candidateDto = new CandidateDto(candidate);
		return candidateDto;
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CandidateDto> findAll() {
		final List<Candidate> candidates = this.candidateService.findAll();
		final List<CandidateDto> candidateDtos = this.convertToCandidateDtoList(candidates);
		return candidateDtos;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insert(final CandidateDto candidateDto) {
		Response.ResponseBuilder builder = null;
		try {
			this.validate(candidateDto);
			final Candidate candidateToInsert = this.toCandidate(candidateDto);
			this.candidateService.save(candidateToInsert);
			builder = Response.ok();
		} catch (final ConstraintViolationException ce) {
			builder = this.createViolationResponse(ce.getConstraintViolations());
		} catch (final ValidationException e) {
			final Map<String, String> responseObj = new HashMap<>();
			responseObj.put("email", "Email taken");
			builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
		} catch (final Exception e) {
			final Map<String, String> responseObj = new HashMap<>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
		}
		return builder.build();
	}

	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CandidateDto> search(@QueryParam("name") final String name,
			@QueryParam("surename") final String surename, @QueryParam("position") final String position) {
		final List<Candidate> candidates = this.candidateService.searchByNameAndSurename(name, surename, position,
				false);
		final List<CandidateDto> candidateDtos = this.convertToCandidateDtoList(candidates);
		return candidateDtos;
	}

	public void setCandidateService(final CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public void setUserSerivce(final UserService userSerivce) {
		this.userSerivce = userSerivce;
	}

	private Candidate toCandidate(final CandidateDto candidateDto) {
		final Candidate candidate = new Candidate();
		final User owner = this.userSerivce.findUserByEmail(candidateDto.getOwnerEmail());
		candidate.setOwner(owner);
		final Address address = new Address();
		address.setCity(candidateDto.getCity());
		address.setState(candidateDto.getState());
		candidate.setAddress(address);
		candidate.setBornDate(candidateDto.getBornDate());
		candidate.setEmail(candidateDto.getEmail());
		candidate.setExpectedSalary(candidateDto.getExpectedSalary());
		candidate.setFiles(candidateDto.getFiles());
		candidate.setImageFileName(candidateDto.getImageFileName());
		candidate.setInfojobsProfileUrl(candidateDto.getInfojobsProfileUrl());
		candidate.setJobProfile(candidateDto.getJobProfile());
		candidate.setLinkedinProfileUrl(candidateDto.getLinkedinProfileUrl());
		candidate.setName(candidateDto.getName());
		candidate.setPhoneNumber(candidateDto.getPhoneNumber());
		candidate.setRating(candidateDto.getRating());
		candidate.setSalary(candidateDto.getSalary());
		candidate.setSkype(candidateDto.getSkype());
		candidate.setSurename(candidateDto.getSurename());
		candidate.setTags(candidateDto.getTags());
		return candidate;
	}
}