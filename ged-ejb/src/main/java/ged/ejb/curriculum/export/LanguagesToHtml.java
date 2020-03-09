package ged.ejb.curriculum.export;

import java.util.Set;

import ged.ejb.curriculum.Language;

public class LanguagesToHtml extends AbstractHtmlPrinter {

	String print(final Set<Language> languages) {
		final StringBuilder sb = new StringBuilder();
		sb.append(this.openDiv("languages"));
		sb.append("<h1>Idiomas</h1>");
		sb.append(this.openDiv("data"));
		for (final Language language : languages) {
			sb.append(this.printLanguage(language));
		}
		sb.append(this.closeDiv());
		sb.append(this.closeDiv());
		return sb.toString();
	}

	String printLanguage(final Language language) {
		return this.openDiv("language") + this.printName(language.getName())
				+ this.printLevel(language.getLevel().name()) + this.closeDiv();
	}

	String printLevel(final String level) {
		return this.openSpan("level") + level + this.closeSpan();
	}

	String printName(final String name) {
		return this.openSpan("name") + name + this.closeSpan();
	}
}
