package es.nivel36.laie.ejb.export.acquirer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Isabel
 *
 */
public final class ExportData {

	private final List<String> idLabels;
	
	private final List<Item> items;
	
	public ExportData(final List<String> idLabels, final List<Item> items) {
		super();
		Objects.requireNonNull(idLabels);
		Objects.requireNonNull(items);
		this.idLabels = idLabels;
		this.items = items;
	}

	public List<String> getIdLabels() {
		return idLabels;
	}
	
	public List<Item> getItems() {
		return items;
	}
	
	@Override
	public String toString() {
		return "ExportData [idLabels=" + idLabels + ", items=" + items + "]";
	}
	
	public static class Item {
		
		private final List<Object> value = new ArrayList<>();
		
		public void add(final Object object) {
			getValue().add(object);
		}
		
		public List<Object> getValue() {
			return value;
		}

		@Override
		public String toString() {
			return "Item [value=" + value + "]";
		}
	}
}
