package ged.ejb.curriculum;

import static ged.ejb.core.util.Parameters.map;

import java.util.Objects;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class CurriculumDao extends AbstractDao<Curriculum> {

	public Curriculum findByCandidate(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		return this.findByQuery(Curriculum.class, "Curriculum.findByCandidate", map("candidate", candidate));
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