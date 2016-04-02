package ged.web.core.view;

import java.io.Serializable;
import java.util.List;

import ged.ejb.core.model.AbstractEntity;

public class Paginator<T extends AbstractEntity> implements Serializable {

	private static final long serialVersionUID = 7195309415237383376L;

	protected int currentPage;

	protected List<T> dataList;

	protected List<T> entities;

	protected Integer[] pages;

	protected int rowsPerPage;

	public Paginator() {
	}

	public Paginator(final int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public void firstPage() {
		this.currentPage = 0;
		trimList();
	}

	public int getCurrentPage() {
		return this.currentPage;
	}

	public List<T> getDataList() {
		return this.dataList;
	}

	public List<T> getEntities() {
		return this.entities;
	}

	public Integer[] getPages() {
		return this.pages;
	}

	public int getPagesSize() {
		return this.pages.length;
	}

	public int getRowsPerPage() {
		return this.rowsPerPage;
	}

	public void gotoPage(final int page) {
		this.currentPage = page;
		trimList();
	}

	public void lastPage() {
		this.currentPage = this.pages.length - 1;
		trimList();
	}

	public void nextPage() {
		final int maxPage = this.pages.length - 1;
		if (this.currentPage < maxPage) {
			this.currentPage++;
		}
		trimList();
	}

	public void previousPage() {
		if (this.currentPage > 0) {
			this.currentPage--;
		}
		trimList();
	}

	public void rowsPerPageChange() {
		this.currentPage = 0;
	}

	public void setEntities(final List<T> entities) {
		this.entities = entities;
		this.trimList();
		this.setPaginationSize();
	}

	public void setPaginationSize() {
		final int size = (int) Math.ceil(this.entities.size() / (double) this.rowsPerPage);
		this.pages = new Integer[size];
		for (int i = 0; i < size; i++) {
			this.pages[i] = i;
		}
	}

	public void setRowsPerPage(final int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public void trimList() {
		final int size = this.entities.size();
		final int firstRow = this.currentPage * this.rowsPerPage;
		final int lastRow = Math.min(size - firstRow, this.rowsPerPage);
		this.dataList = this.entities.subList(firstRow, firstRow + lastRow);
	}
}
