package ged.ejb.curriculum.language;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Stateless
public class LanguageService extends AbstractService<Language> {

	@Inject
	@Repository
	private LanguageDao languageDao;

	@Override
	protected AbstractDao<Language> getDao() {
		return this.languageDao;
	}

	public void setLanguageDao(final LanguageDao languageDao) {
		this.languageDao = languageDao;
	}
}