package ged.ejb.export;

import java.util.List;

import ged.ejb.core.model.Dao;

public interface ExportFieldDao extends Dao<ExportField> {

	List<ExportField> findAllByExport(final Export export);
}
