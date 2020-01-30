package ged.ejb.core.document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Stateless
public class DocumentTemplateService extends AbstractService<DocumentTemplate> {

	@Inject
	@Repository
	private DocumentTemplateDao documentTemplateDao;

	@Override
	protected AbstractDao<DocumentTemplate> getDao() {
		return documentTemplateDao;
	}

	public void setDocumentDao(DocumentTemplateDao documentTemplateDao) {
		this.documentTemplateDao = documentTemplateDao;
	}

	public DocumentTemplate findDocumentByName(final String name) {
		Objects.requireNonNull(name);
		return this.documentTemplateDao.findDocumentTemplateByName(name);
	}

	public File export(DocumentTemplate document, Set<TemplateTag> parameters) {
		Objects.requireNonNull(document);
		final String documentText = document.getText();
		final String textWithReplacedCustomTextValues = replaceCustomText(documentText, parameters);
		return createPdf(textWithReplacedCustomTextValues);
	}

	private File createPdf(final String text) {
		try {
			final File pdfDest = Files.createTempFile("pdf_converter_", ".pdf").toFile();
			final ConverterProperties converterProperties = new ConverterProperties();
			return htmlConverter(text, pdfDest, converterProperties);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private File htmlConverter(final String text, final File pdfDest, final ConverterProperties converterProperties) {
		try (final OutputStream outputStream = new FileOutputStream(pdfDest)) {
			HtmlConverter.convertToPdf(text, outputStream, converterProperties);
			return pdfDest;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private String replaceCustomText(String text, Set<TemplateTag> parameters) {
		for (final TemplateTag tag : parameters) {
			text = text.replaceAll(tag.getValue(), tag.getText());
		}
		return text;
	}
}
