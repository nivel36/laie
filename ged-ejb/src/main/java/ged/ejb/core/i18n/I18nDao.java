package ged.ejb.core.i18n;

import javax.persistence.NoResultException;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;
import ged.ejb.core.util.Parameters;

@Repository
public class I18nDao extends AbstractDao<I18nString> {

	public I18nString find(final String key, final String locale) {
		if ((key == null) || key.isEmpty()) {
			return null;
		}
		try {
			return this.findByQuery(I18nString.class, "I18n.findByKeyAndLocale",
					Parameters.map("key", key).and("locale", locale));
		} catch (final NoResultException e) {
			return null;
		}
	}

	@Override
	protected Class<I18nString> getType() {
		return I18nString.class;
	}

	@Override
	public String[] searchFields() {
		return new String[] { "text" };
	}
}
