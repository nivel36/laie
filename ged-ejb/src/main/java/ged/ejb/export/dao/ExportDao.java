package ged.ejb.export.dao;

import ged.ejb.core.model.Dao;
import ged.ejb.export.entity.Export;

public interface ExportDao extends Dao<Export> {

	Export findByExportName(final String exportName);
}
