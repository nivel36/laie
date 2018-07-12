package ged.ejb.core.i18n;

import ged.ejb.core.model.Dao;

public interface I18nDao extends Dao<I18nString> {

	I18nString find(String key, String locale);

}
