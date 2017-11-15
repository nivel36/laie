package ged.ejb.core;

public class SearchCondition {

	public static enum Condition {
		AND, OR
	};

	private Condition condition;

	private String field;

	private String value;

	public Condition getCondition() {
		return this.condition;
	}

	public String getField() {
		return this.field;
	}

	public String getValue() {
		return this.value;
	}

	public void setCondition(final Condition condition) {
		this.condition = condition;
	}

	public void setField(final String field) {
		this.field = field;
	}

	public void setValue(final String value) {
		this.value = value;
	}

}
