package ged.web.core.view;

import java.util.List;

import javax.annotation.PostConstruct;

import ged.ejb.core.util.Log;

public abstract class AbstractSearchBean<T> extends AbstractBean {

	private static final long serialVersionUID = -2008609936084081329L;

	protected int currentPage;

	protected List<T> dataList;

	protected List<T> entities;

	protected Integer[] pages;

	protected int rowsPerPage;
	
	public void firstPage() {
		currentPage = 0;
		trimList();
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public Integer[] getPages() {
		return pages;
	}

	public int getPagesSize() {
		return pages.length;
	}

	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public void gotoPage(int page) {
		currentPage = page;
		trimList();
	}

	@PostConstruct
	@Log
	public void init() {
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
	
	public void rowsPerPageChange() {
		currentPage = 0;
		search();
	}

	public abstract void search();

	protected void setPaginationSize() {
		int size = (int) Math.ceil(entities.size() / (double) rowsPerPage);
		pages = new Integer[size];
		for (int i = 0; i < size; i++) {
			pages[i] = i;
		}
	}

	public void setRowsPerPage(int rowsPerPage) {
		sessionBean.setRowsPerPage(rowsPerPage);
		this.rowsPerPage = rowsPerPage;
	}

	protected void trimList() {
		int size = entities.size();
		int firstRow = currentPage * rowsPerPage;
		int lastRow = Math.min(size - firstRow, rowsPerPage);
		dataList = entities.subList(firstRow, firstRow + lastRow);
	}
}
