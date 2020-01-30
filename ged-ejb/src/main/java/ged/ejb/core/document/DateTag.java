package ged.ejb.core.document;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class DateTag extends AbstractTemplateTag implements TemplateTag {

	private static final String DATE = "date";

	private final Locale locale;

	public DateTag(Locale locale) {
		this.locale = locale;
	}

	@Override
	public String getText() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
		        .withLocale(locale);
		return LocalDate.now().format(dateTimeFormatter);
	}

	@Override
	public String getTagName() {
		return DATE;
	}
}
