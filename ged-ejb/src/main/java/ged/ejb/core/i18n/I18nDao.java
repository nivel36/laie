package ged.ejb.core.i18n;

import java.util.List;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;
import ged.ejb.core.util.Parameters;

@Repository
public class I18nDao extends AbstractDao<I18nString> {

	public I18nString find(final String key, final String locale) {
		return this.findByQuery(I18nString.class, "I18n.findByKeyAndLocale", Parameters.map("key", key).and("locale", locale));
	}

	@Override
	protected Class<I18nString> getType() {
		return I18nString.class;
	}

	@Override
	public List<I18nString> search(final String searchText) {
		return this.getPersistenceFacade().search(I18nString.class, searchText, "text");
	}
}
