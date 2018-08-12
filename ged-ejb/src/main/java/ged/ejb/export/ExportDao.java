package ged.ejb.export;

import ged.ejb.core.model.Dao;

public interface ExportDao extends Dao<Export> {

	Export findByExportName(final String exportName);
}
