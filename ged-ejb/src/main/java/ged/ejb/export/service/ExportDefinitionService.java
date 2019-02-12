package ged.ejb.export.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;
import ged.ejb.export.dao.ExportDao;
import ged.ejb.export.dto.ExportFieldsOutputBean;
import ged.ejb.export.dto.ExportFieldsOutputBean.ExportFieldItem;
import ged.ejb.export.dto.ExportSaveDefinitionInputBean;
import ged.ejb.export.dto.ExportSaveDefinitionInputBean.SaveDefinitionItem;
import ged.ejb.export.entity.Export;
import ged.ejb.export.entity.ExportDefinition;
import ged.ejb.export.entity.ExportField;
import ged.ejb.export.util.ExportNotFieldFoundException;

@Stateless
public class ExportDefinitionService extends AbstractService<Export> {

	@Inject
	@Repository
	private ExportDao exportDao;
	
	public void saveDefinition(final ExportSaveDefinitionInputBean newDefinition) {
		Objects.requireNonNull(newDefinition);
		final Export export = this.getExportDao().findByExportName(newDefinition.getExportName());
		final Map<Long, ExportField> fieldMap = toExportFieldMap(export.getExportField());
		final Map<Long, ExportDefinition> defMap = toExportDefinitionMap(export.getExportDefinition());
		final Set<Long> defSet = toExportDefinitionSet(export.getExportDefinition());
		for (final SaveDefinitionItem item : newDefinition.getList()) {
			if (!defMap.containsKey(item.getIdField())) {
				export.getExportDefinition().add(createExportDefinitionEntity(item, export, fieldMap));
			}
			else {
				defMap.get(item.getIdField()).setSortOrder(item.getSortOrder());
			}
			defSet.remove(item.getIdField());
		}
		final List<ExportDefinition> toRemoveList = new ArrayList<>();
		if (!defSet.isEmpty()) {
			for (final ExportDefinition def : export.getExportDefinition()) {
				if (defSet.contains(def.getExportField().getId())) {
					toRemoveList.add(def);
				}
			}
			export.getExportDefinition().removeAll(toRemoveList);
		}
	}
	
	
	
	private static Map<Long, ExportDefinition> toExportDefinitionMap(final Set<ExportDefinition> set) {
		final Map<Long, ExportDefinition> map = new HashMap<>();
		for (final ExportDefinition item : set) {
			map.put(item.getExportField().getId(), item);
		}
		return map;
	}
	
	private static Set<Long> toExportDefinitionSet(final Set<ExportDefinition> source) {
		final Set<Long> set = new HashSet<>();
		for (final ExportDefinition item : source) {
			set.add(item.getExportField().getId());
		}
		return set;
	}
	
	private static ExportDefinition createExportDefinitionEntity(final SaveDefinitionItem item, final Export export, final Map<Long, ExportField> fieldMap) {
		final ExportDefinition entity = new ExportDefinition();
		entity.setExport(export);
		if (!fieldMap.containsKey(item.getIdField())) {
			throw new NullPointerException("" + item.getIdField());
		}
		entity.setExportField(fieldMap.get(item.getIdField()));
		entity.setSortOrder(item.getSortOrder());
		export.getExportDefinition().add(entity);
		return entity;
	}

	private static Map<Long, ExportField> toExportFieldMap(final Set<ExportField> set) {
		final Map<Long, ExportField> map = new HashMap<>();
		for (final ExportField item : set) {
			map.put(item.getId(), item);
		}
		return map;
	}

	public ExportFieldsOutputBean findDefinitionByExport(final String exportName) {
		Objects.requireNonNull(exportName);
		final Export export = this.getExportDao().findByExportName(exportName);
		final Set<ExportDefinition> result = export.getExportDefinition();
		final List<ExportFieldItem> list = new ArrayList<>();
		for (final ExportDefinition item : result) {
			list.add(new ExportFieldItem(item.getExportField().getId(), item.getExportField().getLiteralId(), item.getExportField().getAcquirerClass()));
		}
		return new ExportFieldsOutputBean(export.getId(), list);
	}

	public ExportFieldsOutputBean findFieldsByExport(final String exportName) {
		Objects.requireNonNull(exportName);
		final Export export = this.getExportDao().findByExportName(exportName);
		final Set<ExportField> result = export.getExportField();
		if (result.isEmpty()) {
			throw new ExportNotFieldFoundException(exportName);
		}
		final List<ExportFieldItem> list = new ArrayList<>();
		for (final ExportField item : result) {
			if (!item.isDisabled() && (item.getExportDefinition() == null)) {
				list.add(new ExportFieldItem(item.getId(), item.getLiteralId(), item.getAcquirerClass()));
			}
		}
		return new ExportFieldsOutputBean(export.getId(), list);
	}

	@Override
	protected AbstractDao<Export> getDao() {
		return this.exportDao;
	}

	private ExportDao getExportDao() {
		return this.exportDao;
	}

	public void setExportDao(final ExportDao exportDao) {
		this.exportDao = exportDao;
	}
}
