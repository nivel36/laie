package ged.ejb.core.i18n;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Stateless
public class I18nService extends AbstractService<I18nString> {

	@Inject
	@Repository
	private I18nDao i18nDao;

	public I18nString find(final String key, final String locale) {
		try {
			return this.i18nDao.find(key, locale);
		} catch (NoResultException e) {
			final I18nString i18nString = new I18nString();
			i18nString.setKey(key);
			i18nString.setKey(locale);
			i18nString.setText("?" + key + "?");
			return i18nString;
		}
	}

	@Override
	protected AbstractDao<I18nString> getDao() {
		return this.i18nDao;
	}
}
