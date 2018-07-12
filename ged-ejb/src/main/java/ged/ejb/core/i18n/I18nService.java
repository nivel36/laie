package ged.ejb.core.i18n;

import ged.ejb.core.Service;

public interface I18nService extends Service<I18nString> {

	I18nString find(String key, String locale);
}
