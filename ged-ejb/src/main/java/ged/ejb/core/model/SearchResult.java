package ged.ejb.core.model;

import java.util.List;

public class SearchResult<T extends Identifiable> {
	
	private final List<T> resultData;
	
	private final int count;
	
	public SearchResult(List<T> resultData, int count) {
		this.resultData = resultData;
		this.count = count;
	}
	
	public List<T> getResultData() {
		return resultData;
	}

	public int getCount() {
		return count;
	}

}
