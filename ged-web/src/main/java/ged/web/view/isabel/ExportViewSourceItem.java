package ged.web.view.isabel;

import java.util.Objects;

import ged.ejb.export.entity.ExportField;
import ged.web.core.util.Translator;

public final class ExportViewSourceItem implements ExportViewItemI {

	private final Translator translator;
	
	private final String label;
	
	private final ExportField item;
	
	public ExportViewSourceItem(final ExportField item, final Translator translator) {
		super();
		Objects.requireNonNull(item);
		Objects.requireNonNull(translator);
		this.translator = translator;
		this.label = getTranslator().message(item.getLiteralId());
		this.item = item;
	}
	
	
	@Override
	public String getLabel() {
		return label;
	}

	
	@Override
	public ExportField getItem() {
		return item;
	}
	
	private Translator getTranslator() {
		return translator;
	}

	
	@Override
	public String toString() {
		return "ExportViewSourceItem [label=" + label + "]";
	}
}
