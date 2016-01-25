package ged.web.view.candidate;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.core.util.Log;
import ged.ejb.service.candidate.Candidate;
import ged.ejb.service.candidate.CandidateService;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class CandidateSearchBean extends AbstractBean {

	private static final long serialVersionUID = 2434819723782902618L;

	private List<Candidate> candidates;

	private List<Candidate> dataList;

	private String email;

	private String firstSurename;

	private String name;

	private String phoneNumber;

	private int currentPage;

	private int rowsPerPage;

	private Integer[] pages;

	@Inject
	private CandidateService candidateService;

	// ////////////////////////////////////////////////////////////////////////
	// INIT
	// ////////////////////////////////////////////////////////////////////////

	@PostConstruct
	@Log
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

	public List<Candidate> getDataList() {
		return dataList;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstSurename() {
		return firstSurename;
	}

	public String getName() {
		return name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstSurename(String firstSurename) {
		this.firstSurename = firstSurename;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getPagesSize() {
		return pages.length;
	}

	// ////////////////////////////////////////////////////////////////////////
	// ACTIONS
	// ////////////////////////////////////////////////////////////////////////

	public String edit(Candidate candidate) {
		flash.put("candidate", candidate);
		return "candidateEdit?faces-redirect=true";
	}

	public void remove(Candidate candidate) {
		candidateService.delete(candidate);
		search();
	}

	public void clean() {
		email = null;
		firstSurename = null;
		name = null;
		phoneNumber = null;
		search();
	}

	public String newCandidate() {
		return "candidateEdit?faces-redirect=true";
	}

	public void rowsPerPageChange() {
		currentPage = 0;
		search();
	}

	@Log
	public void search() {
		logger.fine("Searching for candidates");
		candidates = candidateService.searchByProperties(name, firstSurename, email,
				phoneNumber);
		trimList();
		setPaginationSize();
	}

	private void setPaginationSize() {
		int size = (int) Math.ceil(candidates.size() / (double) rowsPerPage);
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
		int size = candidates.size();
		int firstRow = currentPage * rowsPerPage;
		int lastRow = Math.min(size - firstRow, rowsPerPage);
		dataList = candidates.subList(firstRow, firstRow + lastRow);
	}
}
