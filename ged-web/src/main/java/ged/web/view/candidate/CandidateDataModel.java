package ged.web.view.candidate;

import java.util.List;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;

@ViewScoped
@ManagedBean
public class CandidateDataModel extends LazyDataModel<Candidate> {

	private static final long serialVersionUID = -6072084300727663488L;

	@Inject
	private transient CandidateService candidateService;

	@Override
	public Candidate getRowData(final String rowKey) {
		return candidateService.find(Long.parseLong(rowKey));
	}

	@Override
	public Object getRowKey(final Candidate candidate) {
		return candidate.getId();
	}

	@Override
	public List<Candidate> load(final int first, final int pageSize, final List<SortMeta> multiSortMeta, final Map<String, Object> filters) {
		return null;
	}

	@Override
	public List<Candidate> load(final int first, final int pageSize, final String sortField, final SortOrder sortOrder, final Map<String, Object> filters) {
		return null;
	}
}
