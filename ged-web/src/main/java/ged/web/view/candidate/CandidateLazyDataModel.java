package ged.web.view.candidate;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.SearchResult;
import ged.ejb.core.model.SortField;

public class CandidateLazyDataModel extends LazyDataModel<Candidate> {

	private static final long serialVersionUID = -6450532500586197930L;

	private CandidateService candidateSerivce;

	private String searchText;

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public CandidateLazyDataModel(CandidateService candidateService) {
		Objects.requireNonNull(candidateService, "CandidateService can't be null");
		this.candidateSerivce = candidateService;
	}

	@Override
	public List<Candidate> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		final Page page = new Page(first + 1, pageSize);
		SearchResult<Candidate> searchResult = candidateSerivce.search(searchText, page, new SortField(sortField, sortOrder == SortOrder.DESCENDING));
		this.setRowCount(searchResult.getCount());
		return searchResult.getResultData();
	}

	public List<Candidate> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
		throw new UnsupportedOperationException("Lazy loading is not implemented.");
	}

	@Override
	public Candidate getRowData(String rowKey) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getRowKey(Candidate object) {
		throw new UnsupportedOperationException();
	}
}
