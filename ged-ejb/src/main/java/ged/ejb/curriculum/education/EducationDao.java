package ged.ejb.curriculum.education;

import java.util.List;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class EducationDao extends AbstractDao<Education> {

	@Override
	protected Class<Education> getType() {
		return Education.class;
	}

	@Override
	public List<Education> search(final String searchText) {
		return this.getPersistenceFacade().search(Education.class, searchText, "description");
	}
}