package ged.web.view.job;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.job.JobOffer;
import ged.ejb.job.JobService;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class JobOfferSearchBean extends AbstractBean {

	private static final long serialVersionUID = 8777365288968792501L;

	private int currentPage;

	private List<JobOffer> dataList;

	private List<JobOffer> jobOffers;

	@Inject
	private JobService jobService;

	private String name;

	private Integer[] pages;

	private int rowsPerPage;

	public void clean() {
		this.name = null;
		search();
	}

	public String edit(final JobOffer jobOffer) {
		this.flash.put("jobOffer", jobOffer);
		return "jobOfferEdit?faces-redirect=true";
	}

	public void firstPage() {
		this.currentPage = 0;
		trimList();
	}

	public int getCurrentPage() {
		return this.currentPage;
	}

	public List<JobOffer> getDataList() {
		return this.dataList;
	}

	public String getName() {
		return this.name;
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

	@PostConstruct
	public void init() {
		this.rowsPerPage = this.sessionBean.getRowsPerPage();
		search();
	}

	public void lastPage() {
		this.currentPage = this.pages.length - 1;
		trimList();
	}

	public String newJobOffer() {
		return "jobOfferEdit?faces-redirect=true";
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

	public void remove(final JobOffer jobOffer) {
		this.jobService.deleteJobOffer(jobOffer);
		search();
	}

	public void rowsPerPageChange() {
		this.currentPage = 0;
		search();
	}

	public void search() {
		this.jobOffers = this.jobService.findAllJobOffers();
		trimList();
		final int size = (int) Math.ceil(this.jobOffers.size() / (double) this.rowsPerPage);
		this.pages = new Integer[size];
		for (int i = 0; i < size; i++) {
			this.pages[i] = i;
		}
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setRowsPerPage(final int rowsPerPage) {
		this.sessionBean.setRowsPerPage(rowsPerPage);
		this.rowsPerPage = rowsPerPage;
	}

	private void trimList() {
		final int size = this.jobOffers.size();
		final int firstRow = this.currentPage * this.rowsPerPage;
		final int lastRow = Math.min(size - firstRow, this.rowsPerPage);
		this.dataList = this.jobOffers.subList(firstRow, firstRow + lastRow);
	}
}
