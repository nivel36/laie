package ged.ejb.candidate;

import java.util.ArrayList;
import java.util.List;

public class CandidateSearch {

	private static class CandidateSearchCondition {

		private static enum Condition {
			AND, OR
		}

		private final Condition condition;

		private final String textCondition;;

		public CandidateSearchCondition(final Condition condition, final String textCondition) {
			this.condition = condition;
			this.textCondition = textCondition;
		}

		public CandidateSearchCondition(final String textCondition) {
			this.condition = Condition.AND;
			this.textCondition = textCondition;
		}
	}

	private List<CandidateSearchCondition> searchConditions = null;

	public CandidateSearch(final String text) {
		this.searchConditions = new ArrayList<>();
		this.searchConditions.add(new CandidateSearchCondition(text));

	}

	public CandidateSearch and(final String text) {
		this.searchConditions.add(new CandidateSearchCondition(
				ged.ejb.candidate.CandidateSearch.CandidateSearchCondition.Condition.AND, text));
		return this;
	}

	public CandidateSearch or(final String text) {
		this.searchConditions.add(new CandidateSearchCondition(
				ged.ejb.candidate.CandidateSearch.CandidateSearchCondition.Condition.OR, text));
		return this;
	}
}
