package ged.web.view.isabel;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DualListModel;

import ged.ejb.export.acquirer.ReportInfo;
import ged.ejb.export.dto.ExportFieldsOutputBean;
import ged.ejb.export.dto.ExportFieldsOutputBean.ExportFieldItem;
import ged.ejb.export.dto.ExportSaveDefinitionInputBean;
import ged.ejb.export.dto.ExportSaveDefinitionInputBean.SaveDefinitionItem;
import ged.ejb.export.service.ExportDefinitionService;
import ged.web.core.util.Translator;
import ged.web.core.view.AbstractView;

/**
 * @author Isabel
 *
 */
@Named
@ViewScoped
public class IsabelView extends AbstractView {

	private static final String EXPORT_NAME = "USERS";

	private static final long serialVersionUID = 1L;

	@Inject
	private transient ExportDefinitionService exportDefinitionService;

	private DualListModel<ExportViewItemI> model;

	private List<ReportInfo> reportsList;

	private ExportDefinitionService getExportDefinitionService() {
		return this.exportDefinitionService;
	}

	public DualListModel<ExportViewItemI> getModel() {
		return this.model;
	}

	public List<ReportInfo> getReportsList() {
		return this.reportsList;
	}

	private Translator getTranslator() {
		return this.translator;
	}

	@PostConstruct
	public void init() {
		this.initialize(EXPORT_NAME);
	}

	private void initialize(final String exportName) {
		this.reportsList = this.getExportDefinitionService().getReportsList();
		final List<ExportViewItemI> source = this
				.initializeList(this.getExportDefinitionService().findFieldsByExport(EXPORT_NAME));
		final List<ExportViewItemI> target = this
				.initializeList(this.getExportDefinitionService().findDefinitionByExport(EXPORT_NAME));
		this.model = new DualListModel<>(source, target);
	}

	private List<ExportViewItemI> initializeList(final ExportFieldsOutputBean outputBean) {
		final List<ExportViewItemI> source = new ArrayList<>();
		for (final ExportFieldItem item : outputBean.getList()) {
			source.add(new ExportViewItem(item, this.getTranslator()));
		}
		return source;
	}

	public void save() {
		final List<ExportViewItemI> currentTarget = this.getModel().getTarget();
		final List<SaveDefinitionItem> list = new ArrayList<>();
		int i = 0;
		for (final ExportViewItemI item : currentTarget) {
			list.add(new SaveDefinitionItem(((ExportFieldItem) item.getItem()).getIdField(), i++));
		}
		this.getExportDefinitionService().saveDefinition(new ExportSaveDefinitionInputBean(EXPORT_NAME, list));
		this.initialize(EXPORT_NAME);
	}

	public void setModel(final DualListModel<ExportViewItemI> campos) {
		this.model = campos;
	}
}
