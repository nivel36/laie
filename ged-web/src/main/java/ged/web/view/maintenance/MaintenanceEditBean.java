package ged.web.view.maintenance;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.NavigationHandler;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.core.MaintenanceService;
import ged.ejb.core.model.AbstractLookupEntity;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class MaintenanceEditBean extends AbstractBean {

	private static final long serialVersionUID = 6411182494923368837L;

	private int currentPage;

	private List<AbstractLookupEntity> dataList;

	private String description;

	private List<AbstractLookupEntity> entities;

	private String key;

	private AbstractLookupEntity maintenance;

	private Class<AbstractLookupEntity> maintenanceClass;

	private String maintenanceClassName;

	@Inject
	private MaintenanceService maintenanceService;

	private Integer[] pages;

	private int rowsPerPage;

	private String value;

	private void error() {
		NavigationHandler navigationHandler = facesContext.getApplication()
				.getNavigationHandler();
		navigationHandler.handleNavigation(facesContext, null,
				"manitenanceIndex?faces-redirect=true");
		facesContext.renderResponse();
	}

	public void firstPage() {
		currentPage = 0;
		trimList();
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public List<AbstractLookupEntity> getDataList() {
		return dataList;
	}

	public String getDescription() {
		return description;
	}

	public List<AbstractLookupEntity> getEntities() {
		return entities;
	}

	public String getKey() {
		return key;
	}

	public AbstractLookupEntity getMaintenance() {
		return maintenance;
	}

	public Integer[] getPages() {
		return pages;
	}

	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public String getValue() {
		return value;
	}

	public void gotoPage(int page) {
		currentPage = page;
		trimList();
	}

	@PostConstruct
	public void init() {
		try {
			maintenanceClass = (Class<AbstractLookupEntity>) Class
					.forName(maintenanceClassName);
		} catch (ClassNotFoundException e) {
			logger.fine("Error converting maintenance class name");
			error();
		}
		rowsPerPage = sessionBean.getRowsPerPage();
		search();
	}

	public void lastPage() {
		currentPage = pages.length - 1;
		trimList();
	}

	public void nextPage() {
		int maxPage = pages.length - 1;
		if (currentPage < maxPage) {
			currentPage++;
		}
		trimList();
	}

	public void previousPage() {
		if (currentPage > 0) {
			currentPage--;
		}
		trimList();
	}

	public void remove(AbstractLookupEntity entity) {
		maintenanceService.delete(entity);
		search();
	}

	public void search() {
		entities = maintenanceService.getAll(maintenanceClass);
	}

	public void select(AbstractLookupEntity entity) {

	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setDataList(List<AbstractLookupEntity> dataList) {
		this.dataList = dataList;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEntities(List<AbstractLookupEntity> entities) {
		this.entities = entities;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setMaintenance(AbstractLookupEntity maintenance) {
		this.maintenance = maintenance;
	}

	public void setMaintenanceClassName(String maintenanceClassName) {
		this.maintenanceClassName = maintenanceClassName;
	}

	public void setPages(Integer[] pages) {
		this.pages = pages;
	}

	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private void trimList() {
		int size = entities.size();
		int firstRow = currentPage * rowsPerPage;
		int lastRow = Math.min(size - firstRow, rowsPerPage);
		dataList = entities.subList(firstRow, firstRow + lastRow);
	}

}
