package ged.ejb.export.dao;

import static ged.ejb.core.util.Parameters.map;

import java.util.Objects;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;
import ged.ejb.export.entity.Export;
import ged.ejb.export.util.ExportConstants.FindExportByExportName;

@Repository
public class ExportDao extends AbstractDao<Export> {

	public Export findByExportName(final String exportName) {
		Objects.nonNull(exportName);
		return this.findByQuery(Export.class, FindExportByExportName.QUERY_NAME, map(FindExportByExportName.Params.EXPORT_NAME, exportName));
	}

	@Override
	protected Class<Export> getType() {
		return Export.class;
	}
}
