package es.nivel36.laie.web.reports;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.user.User;

public class UserReport extends AbstractReport {

	private static CellStyle createBorderedStyle(final Workbook wb) {
		final CellStyle style = wb.createCellStyle();
		style.setBorderRight(BorderStyle.THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderBottom(BorderStyle.THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(BorderStyle.THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(BorderStyle.THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		return style;
	}

	private final List<JobOffer> jobOffers;

	private final User user;

	public UserReport(final User user, final List<JobOffer> jobOffers) {
		super("", user.getFullName() + "_report.xls".replace(" ", "_"));
		this.user = user;
		this.jobOffers = jobOffers;
	}

	public File create() throws IOException {
		final Sheet sheet = this.wb.createSheet(this.user.getFullName());
		Row row = sheet.createRow(1);
		Cell cell = row.createCell(1);
		cell.setCellValue("Nombre:");
		cell.setCellStyle(this.getBoldStyle(this.wb));
		cell = row.createCell(2);
		cell.setCellValue(this.user.getFullName());
		cell.setCellStyle(createBorderedStyle(this.wb));

		row = sheet.createRow(2);
		cell = row.createCell(1);
		cell.setCellValue("Email:");
		cell.setCellStyle(this.getBoldStyle(this.wb));
		cell = row.createCell(2);
		cell.setCellValue(this.user.getEmail());
		cell.setCellStyle(createBorderedStyle(this.wb));

		row = sheet.createRow(3);
		cell = row.createCell(1);
		cell.setCellValue("Responsable:");
		cell.setCellStyle(this.getBoldStyle(this.wb));
		cell = row.createCell(2);
		if (this.user.getManager() != null) {
			cell.setCellValue(this.user.getManager().getFullName());
		}
		cell.setCellStyle(createBorderedStyle(this.wb));

		row = sheet.createRow(6);
		cell = row.createCell(1);
		cell.setCellValue("Cliente");
		cell.setCellStyle(this.getHeaderStyle(this.wb));
		cell = row.createCell(2);
		cell.setCellValue("Nombre");
		cell.setCellStyle(this.getHeaderStyle(this.wb));
		cell = row.createCell(3);
		cell.setCellValue("Fecha apertura");
		cell.setCellStyle(this.getHeaderStyle(this.wb));
		cell = row.createCell(4);
		cell.setCellValue("Fecha cierre");
		cell.setCellStyle(this.getHeaderStyle(this.wb));
		cell = row.createCell(5);
		cell.setCellValue("Plazas");
		cell.setCellStyle(this.getHeaderStyle(this.wb));
		cell = row.createCell(6);
		cell.setCellValue("Ciudad");
		cell.setCellStyle(this.getHeaderStyle(this.wb));
		cell = row.createCell(7);
		cell.setCellValue("Provincia");
		cell.setCellStyle(this.getHeaderStyle(this.wb));
		cell = row.createCell(8);
		cell.setCellValue("Descripción");
		cell.setCellStyle(this.getHeaderStyle(this.wb));
		int i = 7;
		final DataFormat df = this.wb.createDataFormat();
		for (final JobOffer jobOffer : this.jobOffers) {
			row = sheet.createRow(i++);
			cell = row.createCell(1);
			cell.setCellValue(jobOffer.getClient().getName());
			cell = row.createCell(2);
			cell.setCellValue(jobOffer.getTitle());
			cell = row.createCell(3);
			final CellStyle style = this.wb.createCellStyle();
			style.setDataFormat(df.getFormat("mm-YYYY"));
			cell.setCellStyle(style);
			cell.setCellValue(1L); // TODO: corregir
			cell = row.createCell(4);
			cell.setCellStyle(style);
			cell.setCellValue(1L); // TODO: corregir
			cell = row.createCell(5);
			cell.setCellValue(jobOffer.getPlaces());
			cell = row.createCell(6);
			cell.setCellValue(jobOffer.getAddress().getCity());
			cell = row.createCell(7);
			cell.setCellValue(jobOffer.getState().getName());
			cell = row.createCell(8);
			cell.setCellValue(jobOffer.getDescription());
		}

		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);
		sheet.autoSizeColumn(5);
		sheet.autoSizeColumn(6);
		sheet.autoSizeColumn(7);
		sheet.autoSizeColumn(8);

		return this.createFile();
	}

	private CellStyle getBoldStyle(final Workbook wb) {
		CellStyle style;
		final Font headerFont = wb.createFont();
		headerFont.setBold(true);
		style = createBorderedStyle(wb);
		style.setFont(headerFont);
		return style;
	}

	private CellStyle getHeaderStyle(final Workbook wb) {
		CellStyle style;
		final Font headerFont = wb.createFont();
		headerFont.setBold(true);
		style = createBorderedStyle(wb);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setFont(headerFont);
		return style;
	}
}
