package ged.ejb.core.document;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.xml.sax.helpers.DefaultHandler;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Stateless
public class DocumentService extends AbstractService<Document> {

	@Inject
	@Repository
	private DocumentDao documentDao;

	@Inject
	private FopFactory fopFactory;

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

	public File export(Document document, String xsltPath) throws Exception {
		final File tempFile = File.createTempFile("document_", "_pdf");
		try (final OutputStream out = new BufferedOutputStream(new FileOutputStream(tempFile))) {
			final Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);
			final TransformerFactory factory = TransformerFactory.newInstance();
			final File xsltFile = new File(xsltPath);
			final Source xsltSource = new StreamSource(xsltFile);
			final Transformer transformer = factory.newTransformer(xsltSource);
			final String text = document.getText();
			final byte[] textAsBytes = text.getBytes();
			try (final InputStream is = new ByteArrayInputStream(textAsBytes)) {
				final Source src = new StreamSource(is);
				final DefaultHandler defaultHandler = fop.getDefaultHandler();
				final Result res = new SAXResult(defaultHandler);
				transformer.transform(src, res);
			}
		}
		return tempFile;
	}
}
