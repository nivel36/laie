package ged.web.view.isabel;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DualListModel;

import ged.ejb.export.ExportField;
import ged.ejb.export.ExportService;
import ged.web.core.util.Translator;
import ged.web.core.view.AbstractBean;

/**
 * @author Isabel
 *
 */
@Named
@ViewScoped
public class IsabelBean extends AbstractBean {

	private static final long serialVersionUID = 1L;

	private static final String EXPORT_NAME = "USERS";
	
	@Inject
	private transient ExportService exportService;
	
	@Inject
	private transient Translator translator;
	
	private DualListModel<String> campos;
	
	@PostConstruct
	public void init() {
		final List<ExportField> result = getExportService().findFieldsByExport(EXPORT_NAME);
		final List<String> source = initializeSourceList(result);
		final List<String> target = new ArrayList<String>();
		this.campos = new DualListModel<>(source, target);
	}

	private List<String> initializeSourceList(final List<ExportField> result) {
		final List<String> source = new ArrayList<String>();
		for (ExportField item: result) {
			source.add(getTranslator().message(item.getLiteralId()));
		}
		return source;
	}
	
	public void save() {
		// TODO guardar
		System.out.println("guardar");
	}
	
	public DualListModel<String> getCampos() {
		return campos;
	}

	public void setCampos(DualListModel<String> campos) {
		this.campos = campos;
	}

	private ExportService getExportService() {
		return exportService;
	}

	private Translator getTranslator() {
		return translator;
	}
}
