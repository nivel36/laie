package es.nivel36.laie.web.view.isabel;

import java.util.Objects;

import es.nivel36.laie.ejb.export.entity.ExportDefinition;
import es.nivel36.laie.web.core.util.Translator;

public final class ExportViewTargetItem implements ExportViewItemI {

	private final ExportDefinition item;

	private final String label;

	private final Translator translator;

	public ExportViewTargetItem(final ExportDefinition item, final Translator translator) {
		super();
		Objects.requireNonNull(item);
		Objects.requireNonNull(translator);
		this.translator = translator;
		this.label = this.getTranslator().message(item.getExportField().getLiteralId());
		this.item = item;
	}

	@Override
	public ExportDefinition getItem() {
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
		return "ExportViewTargetItem [label=" + this.label + "]";
	}
}
