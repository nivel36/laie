package es.nivel36.laie.ejb.curriculum.export;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;

import es.nivel36.laie.ejb.core.util.ConfigurationProperty;
import es.nivel36.laie.ejb.curriculum.Curriculum;
import es.nivel36.laie.ejb.curriculum.CurriculumTemplate;

@Stateless
public class CurriculumHtmlExporter implements CurriculumExporter {

	@Inject
	@ConfigurationProperty("image.directory")
	private String imagePath;

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

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
		final String curriculumHtml = new CurriculumToHtml().print(curriculum, imagePath);
		final String css = template.getCss();

		final StringBuilder sb = new StringBuilder();
		sb.append("<html><head><style>").append(css).append("</style></head><body>").append(curriculumHtml)
				.append("</body></html>");
		String html = sb.toString();
		logger.info(html);
		return this.createPdf(html);
	}

	private File htmlConverter(final String text, final File pdfDest, final ConverterProperties converterProperties) {
		try (final OutputStream outputStream = new FileOutputStream(pdfDest)) {
			HtmlConverter.convertToPdf(text, outputStream, converterProperties);
			return pdfDest;
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
