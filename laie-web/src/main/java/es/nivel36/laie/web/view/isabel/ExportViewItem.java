package es.nivel36.laie.web.view.isabel;

import java.util.Objects;

import es.nivel36.laie.ejb.export.dto.ExportFieldsOutputBean.ExportFieldItem;
import es.nivel36.laie.web.core.util.Translator;

public final class ExportViewItem implements ExportViewItemI {

	private final ExportFieldItem item;

	private final String label;

	private final Translator translator;

	public ExportViewItem(final ExportFieldItem item, final Translator translator) {
		super();
		Objects.requireNonNull(item);
		Objects.requireNonNull(translator);
		this.translator = translator;
		this.label = this.getTranslator().message(item.getLiteralId());
		this.item = item;
	}

	@Override
	public ExportFieldItem getItem() {
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
		return "ExportViewItem [label=" + this.label + "]";
	}
}
