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
import ged.excel.write.GenerateReport;
import ged.excel.write.ExcelData.ItemData;
import ged.excel.write.inner.GedWorkbookFactory.WorkbookType;
import ged.web.core.util.Translator;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class SearchUserView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1L;

	private String searchText;

	private UserLazyDataModel users;
	
	@Inject
	private transient ExportService exportService;

	@Inject
	private transient UserService userService;

	public void export() {
		logger.debug("Export users action performed");
		// TODO ivmedina
		Objects.requireNonNull(getUsers()); // TODO ivmedina revisar
		final ExportData exportData = getExportService().getExportUsersData(getUsers().getWrappedData());
		Objects.requireNonNull(exportData);
		final ExcelData excelData = toExcelData(exportData);
		try {
			final byte[] bytes = GenerateReport.generate(excelData, WorkbookType.XLSX_STREAMING);
			Faces.sendFile(bytes, "fichero.xlsx", true);
		} catch (IOException e) {
			addMessage(FacesMessage.SEVERITY_ERROR, "test", "abc");
		}
	}
	
	private ExcelData toExcelData(final ExportData exportData) {
		final List<String> literals = new ArrayList<String>();
		for (String item: exportData.getIdLabels()) {
			final String literal = getTranslator().message(item);
			Objects.requireNonNull(literal);
			literals.add(literal);
		}
		final List<ItemData> values = new ArrayList<ItemData>();
		for (Item item: exportData.getItems()) {
			final ItemData itemData = new ItemData();
			for (Object value: item.getValue()) {
				itemData.add(value);
			}
			values.add(itemData);
		}
		return new ExcelData(literals, values);
	}

	public String getSearchText() {
		return this.searchText;
	}

	public UserLazyDataModel getUsers() {
		return this.users;
	}

	@PostConstruct
	public void init() {
		logger.trace("User search init");
		users = new UserLazyDataModel(userService);
		this.search();
	}

	public void search() {
		logger.debug("Search users action performed");
		users.setSearchText(searchText);
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
	
	private Translator getTranslator() {
		return translator;
	}
	
	public ExportService getExportService() {
		return exportService;
	}

	public void setExportService(ExportService exportService) {
		this.exportService = exportService;
	}
}