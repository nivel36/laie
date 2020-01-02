package ged.ejb.core.document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Stateless
public class DocumentService extends AbstractService<Document> {

	@Inject
	@Repository
	private DocumentDao documentDao;

	@Override
	protected AbstractDao<Document> getDao() {
		return documentDao;
	}

	public void setDocumentDao(DocumentDao documentDao) {
		this.documentDao = documentDao;
	}

	public Document findDocumentByName(final String name) {
		Objects.requireNonNull(name);
		return this.documentDao.findDocumentByName(name);
	}

	public File export(Document document) {
		Objects.requireNonNull(document);
		try {
			final File pdfDest = Files.createTempFile("pdf_converter_", ".pdf").toFile();
			final ConverterProperties converterProperties = new ConverterProperties();
			HtmlConverter.convertToPdf(document.getText(), new FileOutputStream(pdfDest), converterProperties);
			return pdfDest;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
