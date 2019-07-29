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

import ged.ejb.core.model.Page;
import ged.ejb.export.acquirer.ExportData;
import ged.ejb.export.acquirer.ExportData.Item;
import ged.ejb.export.service.ExportService;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.excel.write.ExcelData;
import ged.excel.write.ExcelData.ItemData;
import ged.excel.write.GenerateReport;
import ged.excel.write.inner.GedWorkbookFactory.WorkbookType;
import ged.web.core.util.Translator;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class UserSearchBean extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 2434819723782902618L;

	private String searchText;

	private List<User> users;

	@Inject
	private transient UserService userService;
	
	@Inject
	private transient ExportService exportService;

	public void export() {
		logger.debug("Export users action performed");
		// TODO ivmedina
		Objects.requireNonNull(getUsers()); // TODO ivmedina revisar
		final ExportData exportData = getExportService().getExportUsersData(getUsers());
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

	public List<User> getUsers() {
		return this.users;
	}

	@PostConstruct
	public void init() {
		logger.trace("User search init");
		this.search();
	}

	public void search() {
		logger.debug("Search users action performed");
		this.users = this.userService.search(this.searchText, Page.ALL);
	}

	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}

	public void setUsers(final List<User> users) {
		this.users = users;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}

	public ExportService getExportService() {
		return exportService;
	}

	public void setExportService(ExportService exportService) {
		this.exportService = exportService;
	}
	
	private Translator getTranslator() {
		return translator;
	}
}