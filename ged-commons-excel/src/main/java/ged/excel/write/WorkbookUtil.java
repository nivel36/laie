package ged.excel.write;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * @author Isabel
 *
 */
public final class WorkbookUtil {

	private WorkbookUtil() {
		throw new UnsupportedOperationException();
	}

	public static Sheet createSheet(final Workbook workbook, final String sheetName) {
		Objects.requireNonNull(workbook);
		Objects.requireNonNull(sheetName);
		return workbook.createSheet(sheetName);
	}

	public static byte[] workbookToByteArray(final Workbook workbook) throws IOException {
		Objects.requireNonNull(workbook);
		try (final ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
			workbook.write(bos);
			return bos.toByteArray();
		}
	}

	public static void workbookToFile(final Workbook workbook, final File file) throws IOException {
		Objects.requireNonNull(workbook);
		Objects.requireNonNull(file);
		try (final FileOutputStream fileOut = new FileOutputStream(file)) {
			workbook.write(fileOut);
		}
	}

	public static void closeWorkbook(final Workbook workbook) throws IOException {
		Objects.requireNonNull(workbook);
		if (workbook instanceof SXSSFWorkbook) { // TODO ivmedina. Poner bonito
			((SXSSFWorkbook) workbook).dispose();
		}
		workbook.close();
	}
}
