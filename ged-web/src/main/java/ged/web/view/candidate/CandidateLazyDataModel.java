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

	public CandidateLazyDataModel(final CandidateService candidateService) {
		Objects.requireNonNull(candidateService, "CandidateService can't be null");
		this.candidateSerivce = candidateService;
	}

	@Override
	public Candidate getRowData(final String rowKey) {
		return this.candidateSerivce.find(Integer.parseInt(rowKey));
	}

	@Override
	public Object getRowKey(final Candidate candidate) {
		return candidate.getId();
	}

	@Override
	public List<Candidate> load(final int first, final int pageSize, final List<SortMeta> multiSortMeta,
			final Map<String, Object> filters) {
		throw new UnsupportedOperationException("Lazy loading is not implemented.");
	}

	@Override
	public List<Candidate> load(final int first, final int pageSize, final String sortField, final SortOrder sortOrder,
			final Map<String, Object> filters) {
		final Page page = new Page(first, first + pageSize);
		final SearchResult<Candidate> searchResult = this.candidateSerivce.search(this.searchText, page,
				new SortField(sortField, sortOrder == SortOrder.DESCENDING));
		this.setRowCount(searchResult.getCount());
		return searchResult.getResultData();
	}

	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}
}
