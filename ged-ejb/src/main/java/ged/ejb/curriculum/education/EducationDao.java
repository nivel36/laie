package ged.ejb.curriculum.education;

import java.util.List;
import java.util.Objects;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;
import ged.ejb.core.util.Parameters;
import ged.ejb.curriculum.Curriculum;

@Repository
public class EducationDao extends AbstractDao<Education> {

	public List<Education> findByCurriculum(final Curriculum curriculum) {
		Objects.requireNonNull(curriculum);
		return this.findByQuery(Education.class, "Education.findByCurriculum", Parameters.map("curriculum", curriculum), 0, 0);
	}

	@Override
	protected Class<Education> getType() {
		return Education.class;
	}

	@Override
	public List<Education> search(final String searchText) {
		return this.getPersistenceFacade().search(Education.class, searchText, "description");
	}
}