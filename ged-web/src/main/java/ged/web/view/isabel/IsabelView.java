package ged.web.view.isabel;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DualListModel;

import ged.ejb.export.dto.ExportFieldsOutputBean;
import ged.ejb.export.dto.ExportFieldsOutputBean.ExportFieldItem;
import ged.ejb.export.dto.ExportSaveDefinitionInputBean;
import ged.ejb.export.dto.ExportSaveDefinitionInputBean.SaveDefinitionItem;
import ged.ejb.export.service.ExportService;
import ged.web.core.util.Translator;
import ged.web.core.view.AbstractView;

/**
 * @author Isabel
 *
 */
@Named
@ViewScoped
public class IsabelView extends AbstractView {

	private static final long serialVersionUID = 1L;

	private static final String EXPORT_NAME = "USERS";

	@Inject
	private transient ExportService exportService;

	private DualListModel<ExportViewItemI> model;

	private ExportService getExportService() {
		return exportService;
	}

	public DualListModel<ExportViewItemI> getModel() {
		return model;
	}

	private Translator getTranslator() {
		return translator;
	}

	@PostConstruct
	public void init() {
		initialize(EXPORT_NAME);
	}

	private void initialize(final String exportName) {
		final List<ExportViewItemI> source = initializeList(getExportService().findFieldsByExport(EXPORT_NAME));
		final List<ExportViewItemI> target = initializeList(getExportService().findDefinitionByExport(EXPORT_NAME));
		this.model = new DualListModel<>(source, target);
	}

	private List<ExportViewItemI> initializeList(final ExportFieldsOutputBean outputBean) {
		final List<ExportViewItemI> source = new ArrayList<>();
		for (ExportFieldItem item : outputBean.getList()) {
			source.add(new ExportViewItem(item, getTranslator()));
		}
		return source;
	}

	public void save() {
		final List<ExportViewItemI> currentTarget = getModel().getTarget();
		final List<SaveDefinitionItem> list = new ArrayList<>();
		int i = 0;
		for (ExportViewItemI item : currentTarget) {
			list.add(new SaveDefinitionItem(((ExportFieldItem) item.getItem()).getIdField(), i++));
		}
		getExportService().saveDefinition(new ExportSaveDefinitionInputBean(EXPORT_NAME, list));
		initialize(EXPORT_NAME);
	}

	public void setModel(DualListModel<ExportViewItemI> campos) {
		this.model = campos;
	}
}
