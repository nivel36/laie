package es.nivel36.laie.web.view.isabel;

import java.util.Objects;

import es.nivel36.laie.ejb.export.entity.ExportField;
import es.nivel36.laie.web.core.util.Translator;

public final class ExportViewSourceItem implements ExportViewItemI {

	private final ExportField item;

	private final String label;

	private final Translator translator;

	public ExportViewSourceItem(final ExportField item, final Translator translator) {
		super();
		Objects.requireNonNull(item);
		Objects.requireNonNull(translator);
		this.translator = translator;
		this.label = this.getTranslator().message(item.getLiteralId());
		this.item = item;
	}

	@Override
	public ExportField getItem() {
		return this.item;
	}

	@Override
	public String getLabel() {
		return this.label;
	}

	private Translator getTranslator() {
		return this.translator;
	}

	@Override
	public String toString() {
		return "ExportViewSourceItem [label=" + this.label + "]";
	}
}
