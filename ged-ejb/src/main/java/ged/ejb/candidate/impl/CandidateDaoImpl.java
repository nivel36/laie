package ged.ejb.candidate.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateDao;
import ged.ejb.candidate.FileType;
import ged.ejb.core.Repository;
import ged.ejb.core.model.PersistenceFacade;

@Repository
public class CandidateDaoImpl implements CandidateDao {

	@Inject
	@Repository
	private PersistenceFacade persistenceFacade;

	@Override
	public void deleteCandidate(final Candidate candidate) {
		this.persistenceFacade.delete(candidate);
	}

	@Override
	public List<FileType> findAllFileTypes() {
		return this.persistenceFacade.getAll(FileType.class);
	}

	@Override
	public Candidate findById(final long id) {
		return this.persistenceFacade.getByPrimaryKey(Candidate.class, id);
	}

	@Override
	public Candidate findCandidateAndCurriculumById(final long id) {
		final Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("id", id);
		final Candidate candidate = this.persistenceFacade.getByTypedQuerySingleResult(Candidate.class,
				"Candidate.findById", properties);
		return candidate;
	}

	@Override
	public List<Candidate> findCandidateByNameAndSurename(final String name, final String surename) {
		final Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("name", name);
		properties.put("surename", surename);
		final List<Candidate> candidates = this.persistenceFacade.getByTypedQuery(Candidate.class,
				"Candidate.findByNameAndSurename", properties, 0, 0);
		return candidates;
	}

	@Override
	public void insertCandidate(final Candidate candidate) {
		this.persistenceFacade.insert(candidate);
	}

	@Override
	public Candidate updateCandidate(final Candidate candidate) {
		return this.persistenceFacade.update(candidate);
	}
}
