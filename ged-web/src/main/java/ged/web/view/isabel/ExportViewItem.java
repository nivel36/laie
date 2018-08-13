package ged.web.view.isabel;

import java.util.Objects;

import ged.ejb.export.dto.ExportFieldsOutputBean.ExportFieldItem;
import ged.web.core.util.Translator;

public final class ExportViewItem implements ExportViewItemI {

	private final Translator translator;
	
	private final String label;
	
	private final ExportFieldItem item;
	
	public ExportViewItem(final ExportFieldItem item, final Translator translator) {
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
	public ExportFieldItem getItem() {
		return item;
	}
	
	private Translator getTranslator() {
		return translator;
	}

	@Override
	public String toString() {
		return "ExportViewItem [label=" + label + "]";
	}
}
