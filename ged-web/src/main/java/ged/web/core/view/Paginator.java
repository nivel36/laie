package ged.web.core.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

@FacesComponent("paginator")
public class Paginator extends UINamingContainer implements Serializable {

	private static final long serialVersionUID = 7195309415237383376L;

	private int firstRow;

	private List<?> listElements;

	private int maxRow;

	private int rowCount;

	public void firstPage() {
		firstRow = 0;
	}

	public int getFirstRow() {
		return firstRow;
	}

	public List<?> getListElements() {
		return listElements;
	}

	public int getMaxRow() {
		return maxRow;
	}

	public List<Integer> getPageNumbers() {
		List<Integer> numbers = new ArrayList<Integer>();
		int pages = maxRow / rowCount;
		for (int i = 0; i < pages; i++) {
			numbers.add(i);
		}
		return numbers;
	}

	public List<?> getpaginatedElements() {
		return listElements.subList(firstRow, rowCount);
	}

	public int getRowCount() {
		return rowCount;
	}

	public void lastPage() {
		firstRow = maxRow / rowCount;
	}

	public void nextPage() {
		this.firstRow++;
	}

	public void previousPage() {
		if (firstRow > 0) {
			this.firstRow--;
		}
	}

	public void setFirstRow(int firstRow) {
		this.firstRow = firstRow;
	}

	public void setListElements(List<?> listElements) {
		this.listElements = listElements;
	}

	public void setMaxRow(int maxRow) {
		this.maxRow = maxRow;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
}
