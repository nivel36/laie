package ged.ejb.core.model;

import java.util.List;

public class SearchResult<T extends Identifiable> {
	
	private final int count;
	
	private final List<T> resultData;
	
	public SearchResult(List<T> resultData, int count) {
		this.resultData = resultData;
		this.count = count;
	}
	
	public int getCount() {
		return count;
	}

	public List<T> getResultData() {
		return resultData;
	}

}
