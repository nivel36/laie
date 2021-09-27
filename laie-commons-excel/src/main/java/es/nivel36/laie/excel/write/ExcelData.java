package es.nivel36.laie.excel.write;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Isabel
 *
 */
public final class ExcelData {

	private final List<String> labels;
	
	private final List<ItemData> itemData;
	
	public ExcelData(final List<String> labels, final List<ItemData> itemData) {
		super();
		Objects.requireNonNull(labels);
		Objects.requireNonNull(itemData);
		this.labels = labels;
		this.itemData = itemData;
	}
	
	public List<String> getLabels() {
		return labels;
	}

	public List<ItemData> getItemData() {
		return itemData;
	}

	@Override
	public String toString() {
		return "ExcelData [labels=" + labels + ", itemData=" + itemData + "]";
	}
	
	public static class ItemData {
		
		private final List<Object> values = new ArrayList<>();
		
		public void add(final Object object) {
			getValues().add(object);
		}
		
		public List<Object> getValues() {
			return values;
		}

		@Override
		public String toString() {
			return "ItemData [values=" + values + "]";
		}
	}
}
