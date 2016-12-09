package ged.rest.controller;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
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
@RequestScoped
public class CandidateRestController {

	@Inject
	@Repository
	private CandidateDao candidateDao;

	@Inject
	private EntityManager em;

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Candidate find(@PathParam("id") final Long id) {
		final Candidate candidate = this.candidateDao.find(id);
		this.em.detach(candidate);
		return candidate;
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Candidate> findAll() {
		final List<Candidate> candidates = this.candidateDao.findAll();
		this.em.clear();
		return candidates;
	}

	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Candidate> search(@QueryParam("name") final String name, @QueryParam("surename") final String surename,
			@QueryParam("position") final String position) {
		return this.candidateDao.searchByNameAndSurename(name, surename, position, false);
	}
}
