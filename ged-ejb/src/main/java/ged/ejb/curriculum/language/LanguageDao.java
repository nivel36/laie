package ged.ejb.curriculum.language;

import java.util.List;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.Repository;

@Repository
public class LanguageDao extends AbstractDao<Language> {

	@Override
	protected Class<Language> getType() {
		return Language.class;
	}

	@Override
	public List<Language> search(final String searchText, final Page page) {
		return this.getPersistenceFacade().search(page, Language.class, searchText, "name");
	}
}
