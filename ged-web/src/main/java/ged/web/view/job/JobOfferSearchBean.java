package ged.web.view.job;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.service.job.JobOffer;
import ged.ejb.service.job.JobService;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class JobOfferSearchBean extends AbstractBean {

	private static final long serialVersionUID = 8777365288968792501L;
	
	private List<JobOffer> jobOffers;

	private List<JobOffer> dataList;

	private String name;

	private int currentPage;

	private int rowsPerPage;

	private Integer[] pages;
	
	@Inject
	private JobService jobService;

	// ////////////////////////////////////////////////////////////////////////
	// INIT
	// ////////////////////////////////////////////////////////////////////////

	@PostConstruct
	public void init() {
		rowsPerPage = sessionBean.getRowsPerPage();
		search();
	}

	// ////////////////////////////////////////////////////////////////////////
	// GET AND SET
	// ////////////////////////////////////////////////////////////////////////

	public Integer[] getPages() {
		return pages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public void setRowsPerPage(int rowsPerPage) {
		sessionBean.setRowsPerPage(rowsPerPage);
		this.rowsPerPage = rowsPerPage;
	}
	
	public List<JobOffer> getDataList() {
		return dataList;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public int getPagesSize() {
		return pages.length;
	}

	// ////////////////////////////////////////////////////////////////////////
	// ACTIONS
	// ////////////////////////////////////////////////////////////////////////

	public String edit(JobOffer jobOffer) {
		flash.put("jobOffer", jobOffer);
		return "jobOfferEdit?faces-redirect=true";
	}

	public void remove(JobOffer jobOffer) {
		jobService.delete(jobOffer);
		search();
	}

	public void clean() {
		name = null;
		search();
	}

	public String newJobOffer() {
		return "jobOfferEdit?faces-redirect=true";
	}

	public void rowsPerPageChange() {
		currentPage = 0;
		search();
	}

	public void search() {
		jobOffers = jobService.getAll(JobOffer.class);
		trimList();
		int size = (int) Math.ceil(jobOffers.size() / (double) rowsPerPage);
		pages = new Integer[size];
		for (int i = 0; i < size; i++) {
			pages[i] = i;
		}
	}

	public void nextPage() {
		int maxPage = pages.length - 1;
		if (currentPage < maxPage) {
			currentPage++;
		}
		trimList();
	}

	public void gotoPage(int page) {
		currentPage = page;
		trimList();
	}

	public void previousPage() {
		if (currentPage > 0) {
			currentPage--;
		}
		trimList();
	}

	public void firstPage() {
		currentPage = 0;
		trimList();
	}

	public void lastPage() {
		currentPage = pages.length - 1;
		trimList();
	}

	private void trimList() {
		int size = jobOffers.size();
		int firstRow = currentPage * rowsPerPage;
		int lastRow = Math.min(size - firstRow, rowsPerPage);
		dataList = jobOffers.subList(firstRow, firstRow + lastRow);
	}
}
