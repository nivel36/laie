package ged.ejb.curriculum.export;

public abstract class AbstractHtmlPrinter {

	private static final String CLOSE_DIV = "</div>";

	private static final String CLOSE_HTML_SECTION = "\">";
	private static final String CLOSE_SPAN = "</span>";
	private static final String DIV = "<div>";

	private static final String DIV_CLASS = "<div class=\"";
	private static final String SPAN = "<span>";
	private static final String SPAN_CLASS = "<span class=\"";

	protected String closeDiv() {
		return CLOSE_DIV;
	}

	protected String closeSpan() {
		return CLOSE_SPAN;
	}

	protected String openDiv(final String cssClass) {
		if (cssClass == null) {
			return DIV;
		}
		return DIV_CLASS + cssClass + CLOSE_HTML_SECTION;
	}

	protected String openSpan(final String cssClass) {
		if (cssClass == null) {
			return SPAN;
		}
		return SPAN_CLASS + cssClass + CLOSE_HTML_SECTION;
	}
}
