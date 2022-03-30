package es.nivel36.laie.ejb.export.dao;

import static es.nivel36.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import es.nivel36.core.model.AbstractDao;
import es.nivel36.core.model.Page;
import es.nivel36.core.model.Repository;
import es.nivel36.core.util.Parameters;
import es.nivel36.laie.ejb.export.entity.Export;
import es.nivel36.laie.ejb.export.util.ExportConstants.FindExportByExportName;

@Repository
public class ExportDao extends AbstractDao {

	public Export findByExportName(final String exportName) {
		Objects.nonNull(exportName);
		final Parameters parameters = map(FindExportByExportName.Params.EXPORT_NAME, exportName);
		return this.findByQuery(Export.class, FindExportByExportName.QUERY_NAME, parameters);
	}

	public List<Export> findAll(final Page page) {
		Objects.nonNull(page);
		return this.findAll(Export.class, page);
	}
}