package ged.ejb.curriculum.language;

import java.util.List;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class LanguageDao extends AbstractDao<Language> {

	@Override
	protected Class<Language> getType() {
		return Language.class;
	}

	@Override
	public List<Language> search(final String searchText) {
		return this.getPersistenceFacade().search(Language.class, searchText, "name");
	}
}
