package ged.ejb.curriculum.education;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class EducationDao extends AbstractDao<Education> {

	@Override
	protected Class<Education> getType() {
		return Education.class;
	}

	@Override
	public String[] searchFields() {
		return new String[] { "description" };
	}
}