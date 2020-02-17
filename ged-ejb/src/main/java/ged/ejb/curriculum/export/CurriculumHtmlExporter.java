package ged.ejb.curriculum.export;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;

import javax.ejb.Stateless;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;

import ged.ejb.curriculum.Curriculum;
import ged.ejb.curriculum.CurriculumTemplate;

@Stateless
public class CurriculumHtmlExporter implements CurriculumExporter {

	private File createPdf(final String html) {
		try {
			final File pdfDest = Files.createTempFile("pdf_converter_", ".pdf").toFile();
			final ConverterProperties converterProperties = new ConverterProperties();
			return this.htmlConverter(html, pdfDest, converterProperties);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public File export(final Curriculum curriculum, final CurriculumTemplate template) {
		final String html = new CurriculumToHtml().print(curriculum);
		final String css = template.getCss();

		final StringBuilder sb = new StringBuilder();
		sb.append("<html><head><style>").append(css).append("</style></head><body>").append(html)
				.append("</body></html>");
		return this.createPdf(sb.toString());
	}

	private File htmlConverter(final String text, final File pdfDest, final ConverterProperties converterProperties) {
		try (final OutputStream outputStream = new FileOutputStream(pdfDest)) {
			HtmlConverter.convertToPdf(text, outputStream, converterProperties);
			return pdfDest;
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
