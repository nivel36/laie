package ged.web.view.user;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.export.acquirer.ExportData;
import ged.ejb.export.acquirer.ExportData.Item;
import ged.ejb.export.service.ExportService;
import ged.ejb.user.UserService;
import ged.excel.write.ExcelData;
import ged.excel.write.ExcelData.ItemData;
import ged.excel.write.GenerateReport;
import ged.excel.write.inner.GedWorkbookFactory.WorkbookType;
import ged.web.core.util.Translator;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class SearchUserView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1L;

	@Inject
	private transient ExportService exportService;

	private String searchText;

	private UserLazyDataModel users;

	@Inject
	private transient UserService userService;

	public void export() {
		logger.debug("Export users action performed");
		// TODO ivmedina
		Objects.requireNonNull(this.getUsers()); // TODO ivmedina revisar
		final ExportData exportData = this.getExportService().getExportUsersData(this.getUsers().getWrappedData());
		Objects.requireNonNull(exportData);
		final ExcelData excelData = this.toExcelData(exportData);
		try {
			final byte[] bytes = GenerateReport.generate(excelData, WorkbookType.XLSX_STREAMING);
			Faces.sendFile(bytes, "fichero.xlsx", true);
		} catch (final IOException e) {
			this.addMessage(FacesMessage.SEVERITY_ERROR, "test", "abc");
		}
	}

	public ExportService getExportService() {
		return this.exportService;
	}

	public String getSearchText() {
		return this.searchText;
	}

	private Translator getTranslator() {
		return this.translator;
	}

	public UserLazyDataModel getUsers() {
		return this.users;
	}

	@PostConstruct
	public void init() {
		logger.trace("User search init");
		this.users = new UserLazyDataModel(this.userService);
	}

	public void search() {
		logger.debug("Search users action performed");
		this.users.setSearchText(this.searchText);
	}

	public void setExportService(final ExportService exportService) {
		this.exportService = exportService;
	}

	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}

	public void setUsers(final UserLazyDataModel users) {
		this.users = users;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}

	private ExcelData toExcelData(final ExportData exportData) {
		final List<String> literals = new ArrayList<>();
		for (final String item : exportData.getIdLabels()) {
			final String literal = this.getTranslator().message(item);
			Objects.requireNonNull(literal);
			literals.add(literal);
		}
		final List<ItemData> values = new ArrayList<>();
		for (final Item item : exportData.getItems()) {
			final ItemData itemData = new ItemData();
			for (final Object value : item.getValue()) {
				itemData.add(value);
			}
			values.add(itemData);
		}
		return new ExcelData(literals, values);
	}
}