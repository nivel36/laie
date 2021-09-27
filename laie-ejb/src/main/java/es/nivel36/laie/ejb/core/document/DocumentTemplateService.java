package es.nivel36.laie.ejb.core.document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;

import es.nivel36.laie.ejb.core.AbstractService;
import es.nivel36.laie.ejb.core.Language;
import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Repository;

@Stateless
public class DocumentTemplateService extends AbstractService<DocumentTemplate> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	@Repository
	private DocumentTemplateDao documentTemplateDao;

	private File createPdf(final String text) {
		try {
			final File pdfDest = Files.createTempFile("pdf_converter_", ".pdf").toFile();
			final ConverterProperties converterProperties = new ConverterProperties();
			return this.htmlConverter(text, pdfDest, converterProperties);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public File export(final DocumentTemplate document, final Set<TemplateTag> parameters) {
		Objects.requireNonNull(document);
		logger.debug("Export document template {}", document);
		final String documentText = document.getText();
		final String textWithReplacedCustomTextValues = this.replaceCustomText(documentText, parameters);
		return this.createPdf(textWithReplacedCustomTextValues);
	}

	public DocumentTemplate findDocumentByNameAndLanguage(final String name, final Language language) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(language);
		logger.debug("Find document by name {} and langauge {}", name, language);
		return this.documentTemplateDao.findDocumentTemplateByName(name, language);
	}

	@Override
	protected AbstractDao<DocumentTemplate> getDao() {
		return this.documentTemplateDao;
	}

	private File htmlConverter(final String text, final File pdfDest, final ConverterProperties converterProperties) {
		try (final OutputStream outputStream = new FileOutputStream(pdfDest)) {
			HtmlConverter.convertToPdf(text, outputStream, converterProperties);
			return pdfDest;
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private String replaceCustomText(String text, final Set<TemplateTag> parameters) {
		for (final TemplateTag tag : parameters) {
			text = text.replaceAll(tag.getValue(), tag.getText());
		}
		return text;
	}

	public void setDocumentDao(final DocumentTemplateDao documentTemplateDao) {
		Objects.requireNonNull(documentTemplateDao);
		this.documentTemplateDao = documentTemplateDao;
	}
}
