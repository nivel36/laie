package ged.ejb.export;

import static ged.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import ged.ejb.core.model.AbstractDaoJpa;
import ged.ejb.core.model.Repository;
import ged.ejb.export.ExportConstants.FindFieldsByExport;

@Repository
public class ExportFieldDaoJpa extends AbstractDaoJpa<ExportField> implements ExportFieldDao {

	@Override
	public List<ExportField> findAllByExport(final Export export) {
		Objects.requireNonNull(export);
		return findByQuery(ExportField.class, FindFieldsByExport.QUERY_NAME, map(FindFieldsByExport.Params.EXPORT, export), 0, 0);
	}
	
	@Override
	public List<ExportField> search(final String searchText) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Class<ExportField> getType() {
		return ExportField.class;
	}
}
