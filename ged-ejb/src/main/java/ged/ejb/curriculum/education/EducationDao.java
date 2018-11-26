package ged.ejb.curriculum.education;

import java.util.List;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.Repository;

@Repository
public class EducationDao extends AbstractDao<Education> {

	@Override
	protected Class<Education> getType() {
		return Education.class;
	}

	@Override
	public List<Education> search(final String searchText, final Page page) {
		return this.getPersistenceFacade().search(page, Education.class, searchText, "description");
	}
}