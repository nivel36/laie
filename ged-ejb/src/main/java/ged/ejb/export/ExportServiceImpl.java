package ged.ejb.export;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Repository;

@Stateless
public class ExportServiceImpl extends AbstractService<ExportField> implements ExportService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());
	
	private final ExportDao exportDao;
	
	private final ExportFieldDao exportFieldDao;
	
	@Inject
	public ExportServiceImpl(@Repository ExportDao exportDao, @Repository ExportFieldDao exportFieldDao) {
		Objects.requireNonNull(exportDao);
		Objects.requireNonNull(exportFieldDao);
		this.exportDao = exportDao;
		this.exportFieldDao = exportFieldDao;
		LOGGER.trace("ExportServiceImpl initiated");
	}

	@Override
	public List<ExportField> findFieldsByExport(final String exportName) {
		Objects.requireNonNull(exportName);
		final Export export = exportDao.findByExportName(exportName);
		final List<ExportField> result = exportFieldDao.findAllByExport(export);
		if (result.isEmpty()) {
			throw new ExportNotFieldFoundException(exportName);
		}
		return result;
	}
	
	@Override
	protected Dao<ExportField> getDao() {
		return this.exportFieldDao;
	}
}
