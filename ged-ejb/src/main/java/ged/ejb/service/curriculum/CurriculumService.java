package ged.ejb.service.curriculum;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import ged.ejb.core.GenericServiceImpl;
import ged.ejb.core.model.GenericDao;
import ged.ejb.service.candidate.Candidate;

@Stateless
public class CurriculumService extends GenericServiceImpl {

	@Inject
	private GenericDao genericDao;

	public Curriculum getByCandidate(Candidate candidate) {
		Curriculum curriculum = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("candidateId", candidate.getId());
			curriculum = genericDao.getByTypedQuerySingleResult(
					Curriculum.class, "Curriculum.getByCandidateId", params);
		} catch (NoResultException ex) {
			curriculum = null;
		}
		return curriculum;
	}
}
