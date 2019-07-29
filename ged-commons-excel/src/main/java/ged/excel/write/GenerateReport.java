package ged.excel.write;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import ged.excel.write.ExcelData.ItemData;
import ged.excel.write.inner.GedWorkbookFactory;
import ged.excel.write.inner.GedWorkbookFactory.WorkbookType;

/**
 * @author Isabel
 *
 */
public final class GenerateReport {

	private GenerateReport() {
		throw new UnsupportedOperationException();
	}
	
	public static byte[] generate(final ExcelData data, final WorkbookType type) throws IOException {
		Objects.requireNonNull(data);
		Objects.requireNonNull(type);
		final Workbook workbook = GedWorkbookFactory.create(type);
		final Sheet sheet = WorkbookUtil.createSheet(workbook, "TEST");
		int rowIndex = 0;
		final Row headerRow = sheet.createRow(rowIndex++);
		int i = 0;
		for (String label: data.getLabels()) {
			final Cell cell = headerRow.createCell(i++);
			cell.setCellValue(label);
		}
		for (ItemData item: data.getItemData()) {
			writeRow(sheet.createRow(rowIndex++), item);
		}
		final byte[] bytes = WorkbookUtil.workbookToByteArray(workbook);
		WorkbookUtil.closeWorkbook(workbook);
		return bytes;
	}
	
	private static void writeRow(final Row row, final ItemData itemData) {
		int columnIndex = 0;
		for (Object value: itemData.getValues()) {
			writeCell(row.createCell(columnIndex++), value);
		}
	}
	
	private static void writeCell(final Cell cell, final Object value) {
		if (value != null) {
			if (value instanceof String)  {
				cell.setCellValue((String) value);
			} else {
				System.out.println("tipo no valido " + value.getClass()); // TODO ivmedina (runtime)
			}
		}
	}
}
