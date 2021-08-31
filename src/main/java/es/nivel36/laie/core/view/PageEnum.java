package es.nivel36.laie.core.view;

public enum PageEnum {

	CHANGE_PASSWORD("/changePassword"), //
	CONFIG("/config"), //
	ERROR("/error"), //
	INDEX("/index"), //
	LOGIN("/login"), //
	USER("/user/view"), //
	USER_ADD("/user/add"), //
	USER_EDIT("/user/edit"), //
	USER_SEARCH("/user/search"), //
	USER_SELECT("/user/select");

	private static final String XHTML = ".xhtml";

	private String url;

	PageEnum(final String url) {
		this.url = url + XHTML;
	}
	
	public String getUrl() {
		return this.url;
	}
}
