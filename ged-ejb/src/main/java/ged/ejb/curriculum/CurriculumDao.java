package ged.ejb.curriculum;

import static ged.ejb.core.util.Parameters.map;

import java.util.Objects;

import javax.persistence.NoResultException;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class CurriculumDao extends AbstractDao<Curriculum> {

	public Curriculum findByCandidate(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		try {
			return this.findByQuery(Curriculum.class, "Curriculum.findByCandidate", map("candidate", candidate));
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Curriculum findByUid(final String uid) {
		Objects.requireNonNull(uid);
		return this.findByQuery(Curriculum.class, "Curriculum.findByUid", map("uid", uid));
	}

	@Override
	public Class<Curriculum> getType() {
		return Curriculum.class;
	}

	@Override
	public String[] searchFields() {
		return new String[] {};
	}
}