package ged.web.reports;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

public class AbstractReport {

	private final String fileName;

	private final String path;

	protected final Workbook wb = new HSSFWorkbook();

	public AbstractReport(final String path, final String fileName) {
		this.path = path;
		this.fileName = fileName;
	}

	protected File createFile() {
		final File file = new File(this.path + this.fileName);
		try (FileOutputStream out = new FileOutputStream(file)) {
			this.wb.write(out);
			this.wb.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	protected String getFileName() {
		return this.fileName;
	}

	protected String getPath() {
		return this.path;
	}

}
