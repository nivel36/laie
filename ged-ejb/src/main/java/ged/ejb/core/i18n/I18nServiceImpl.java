package ged.ejb.core.i18n;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.Dao;

@Stateless
public class I18nServiceImpl extends AbstractService<I18nString> implements I18nService {

	@Inject
	private I18nDao i18nDao;

	@Override
	public I18nString find(final String key, final String locale) {
		return this.i18nDao.find(key, locale);
	}

	@Override
	protected Dao<I18nString> getDao() {
		return this.i18nDao;
	}
}
