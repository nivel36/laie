package es.nivel36.laie.ejb.core.document;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;

import es.nivel36.laie.ejb.core.Language;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class DocumentTemplateService {

	private static final Logger logger = LoggerFactory.getLogger(DocumentTemplateService.class);

	private @Inject DocumentTemplateDao documentTemplateDao;

	public void addDocumentTemplate(final DocumentTemplate template) {
		Objects.requireNonNull(template);
		logger.debug("Add document template {}", template);
		this.documentTemplateDao.insert(template);
	}

	public DocumentTemplate updateDocumentTemplate(final DocumentTemplate template) {
		Objects.requireNonNull(template);
		logger.debug("Update document template {}", template);
		return this.documentTemplateDao.update(template);
	}

	public void deleteDocumentTemplate(final DocumentTemplate template) {
		this.deleteDocumentTemplate(template, false);
	}

	public void deleteDocumentTemplate(final DocumentTemplate template, boolean allLanguages) {
		Objects.requireNonNull(template);
		if (allLanguages) {
			logger.debug("All document templates will be deleted");
			final List<DocumentTemplate> documents = this.documentTemplateDao
					.findDocumentTemplateByName(template.getName());
			for (final DocumentTemplate document : documents) {
				logger.debug("Delete document template {}", document);
				this.documentTemplateDao.delete(DocumentTemplate.class, document);
			}
		} else {
			logger.debug("Delete document template {}", template);
			this.documentTemplateDao.delete(DocumentTemplate.class, template);
		}
	}

	public DocumentTemplate findDocumentByNameAndLanguage(final String name, final Language language) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(language);
		logger.debug("Find document by name {} and langauge {}", name, language);
		return this.documentTemplateDao.findDocumentTemplateByNameAndLanguage(name, language);
	}

	public ByteArrayOutputStream export(final DocumentTemplate document, final Set<TemplateTag> parameters) {
		Objects.requireNonNull(document);
		logger.debug("Export document template {}", document);
		final String documentText = document.getText();
		final String textWithReplacedCustomTextValues = this.replaceCustomText(documentText, parameters);
		return this.createPdf(textWithReplacedCustomTextValues);
	}

	private String replaceCustomText(String text, final Set<TemplateTag> parameters) {
		for (final TemplateTag tag : parameters) {
			final String value = tag.getValue();
			final String text2 = tag.getText();
			logger.trace("Replacing {} by {}", value, text);
			text = text.replaceAll(value, text2);
		}
		return text;
	}

	private ByteArrayOutputStream createPdf(final String text) {
		final ConverterProperties converterProperties = new ConverterProperties();
		return this.htmlConverter(text, converterProperties);
	}

	private ByteArrayOutputStream htmlConverter(final String text, final ConverterProperties converterProperties) {
		try (final ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			HtmlConverter.convertToPdf(text, outputStream, converterProperties);
			return outputStream;
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public void setDocumentDao(final DocumentTemplateDao documentTemplateDao) {
		Objects.requireNonNull(documentTemplateDao);
		this.documentTemplateDao = documentTemplateDao;
	}
}