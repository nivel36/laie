package ged.rest.controller;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateDao;
import ged.ejb.core.model.Repository;

@Path("candidates")
@ApplicationScoped
public class CandidateRestController {

	@Inject
	@Repository
	private CandidateDao candidateDao;

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Candidate find(@PathParam("id") final long id) {
		return this.candidateDao.find(id);
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Candidate> findAll() {
		return this.candidateDao.findAll();
	}

	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Candidate> search(@QueryParam("name") final String name, @QueryParam("surename") final String surename,
			@QueryParam("position") final String position) {
		return this.candidateDao.searchByNameAndSurename(name, surename, position, false);
	}
}
