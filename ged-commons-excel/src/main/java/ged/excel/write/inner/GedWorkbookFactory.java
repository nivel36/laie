package ged.excel.write.inner;

import java.util.Objects;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author Isabel
 *
 */
public final class GedWorkbookFactory {

	public enum WorkbookType { XLS, XLSX_MEMORY, XLSX_STREAMING };
	
	private static final CreateWorkbookI XLS_CREATOR = new CreateXlsWorkbook();
	
	private static final CreateWorkbookI XLSX_MEMORY_CREATOR = new CreateXlsxMemoryWorkbook();
	
	private static final CreateWorkbookI XLSX_STREAMING_CREATOR = new CreateXlsxStreamingWorkbook();
	
	private GedWorkbookFactory() {
		throw new UnsupportedOperationException();
	}
	
	public static Workbook create(final WorkbookType type) {
		Objects.requireNonNull(type);
		switch (type) {
		case XLS:
			return XLS_CREATOR.create();
		case XLSX_MEMORY:
			return XLSX_MEMORY_CREATOR.create();
		case XLSX_STREAMING:
			return XLSX_STREAMING_CREATOR.create();
		default:
			throw new IllegalArgumentException(type.name());
		}
	}
	
	private static interface CreateWorkbookI {
		Workbook create();
	}
	
	private static class CreateXlsWorkbook implements CreateWorkbookI{

		@Override
		public Workbook create() {
			return new HSSFWorkbook();
		}
	}
	
	private static class CreateXlsxMemoryWorkbook implements CreateWorkbookI{

		@Override
		public Workbook create() {
			return new XSSFWorkbook();
		}
	}
		
	private static class CreateXlsxStreamingWorkbook implements CreateWorkbookI{

		@Override
		public Workbook create() {
			return new SXSSFWorkbook();
		}
	}
}
