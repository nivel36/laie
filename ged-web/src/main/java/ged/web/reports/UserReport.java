package ged.web.reports;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import ged.ejb.user.User;

public class UserReport {

	private final User user;

	public UserReport(final User user) {
		this.user = user;
	}

	public File create() {
		final Workbook wb = new HSSFWorkbook();
		final Sheet sheet = wb.createSheet(this.user.getFullName());

		String fileName = this.user.getFullName() + "_report.xls";
		fileName = fileName.replace(" ", "_");
		final File file = new File(fileName);
		try (FileOutputStream out = new FileOutputStream(file)) {
			wb.write(out);
			wb.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return file;
	}
}
