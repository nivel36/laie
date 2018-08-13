package ged.web.view.isabel;

import java.util.Objects;

import ged.ejb.export.entity.ExportDefinition;
import ged.web.core.util.Translator;

public final class ExportViewTargetItem implements ExportViewItemI {

	private final Translator translator;
	
	private final String label;
	
	private final ExportDefinition item;
	
	public ExportViewTargetItem(final ExportDefinition item, final Translator translator) {
		super();
		Objects.requireNonNull(item);
		Objects.requireNonNull(translator);
		this.translator = translator;
		this.label = getTranslator().message(item.getExportField().getLiteralId());
		this.item = item;
	}
	
	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public ExportDefinition getItem() {
		return item;
	}
	
	private Translator getTranslator() {
		return translator;
	}

	@Override
	public String toString() {
		return "ExportViewTargetItem [label=" + label + "]";
	}
}
