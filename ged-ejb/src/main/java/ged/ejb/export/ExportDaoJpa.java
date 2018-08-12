package ged.ejb.export;

import static ged.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import ged.ejb.core.model.AbstractDaoJpa;
import ged.ejb.core.model.Repository;
import ged.ejb.export.ExportConstants.FindExportByExportName;

@Repository
public class ExportDaoJpa extends AbstractDaoJpa<Export> implements ExportDao {

	@Override
	public Export findByExportName(final String exportName) {
		Objects.nonNull(exportName);
		return findByQuery(Export.class, FindExportByExportName.QUERY_NAME, map(FindExportByExportName.Params.EXPORT_NAME, exportName));
	}
	
	@Override
	public List<Export> search(final String searchText) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Class<Export> getType() {
		return Export.class;
	}
}
