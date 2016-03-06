package ged.ejb.core.i18n;

import java.util.List;

import javax.ejb.Local;

@Local
public interface I18nService {

	public List<I18nString> findAll();

}
