package ged.ejb.curriculum;

import static ged.ejb.core.util.Parameters.map;

import java.util.Objects;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class EducationDao extends AbstractDao<Education> {

	public Education findByUid(final String uid) {
		Objects.requireNonNull(uid);
		return this.findByQuery(Education.class, "Education.findByUid", map("uid", uid));
	}

	@Override
	protected Class<Education> getType() {
		return Education.class;
	}

	@Override
	public String[] searchFields() {
		return new String[] { "description" };
	}
}