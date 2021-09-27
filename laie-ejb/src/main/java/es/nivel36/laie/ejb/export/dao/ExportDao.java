package es.nivel36.laie.ejb.export.dao;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Repository;
import es.nivel36.laie.ejb.export.entity.Export;
import es.nivel36.laie.ejb.export.util.ExportConstants.FindExportByExportName;

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
