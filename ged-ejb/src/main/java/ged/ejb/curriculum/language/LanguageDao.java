package ged.ejb.curriculum.language;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class LanguageDao extends AbstractDao<Language> {

	@Override
	protected Class<Language> getType() {
		return Language.class;
	}

	@Override
	public String[] searchFields() {
		return new String[] { "name" };
	}
}
