package ged.ejb.core.i18n;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Stateless
public class I18nService extends AbstractService<I18nString> {

	@Inject
	@Repository
	private I18nDao i18nDao;

	public I18nString find(final String key, final String locale) {
		return this.i18nDao.find(key, locale);
	}

	@Override
	protected AbstractDao<I18nString> getDao() {
		return this.i18nDao;
	}
}
