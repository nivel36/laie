package es.nivel36.laie.ejb.core.document;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class DateTag extends AbstractTemplateTag implements TemplateTag {

	private static final String DATE = "date";

	private final Locale locale;

	public DateTag(final Locale locale) {
		this.locale = locale;
	}

	@Override
	public String getTagName() {
		return DATE;
	}

	@Override
	public String getText() {
		final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(this.locale);
		final LocalDate now = LocalDate.now();
		return now.format(formatter);
	}
}
