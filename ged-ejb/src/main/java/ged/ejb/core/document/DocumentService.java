package ged.ejb.core.document;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.xml.sax.SAXException;

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

	public File export() throws SAXException, IOException, TransformerException {

		// Step 1: Construct a FopFactory by specifying a reference to the configuration
		// file
		// (reuse if you plan to render multiple documents!)
		FopFactory fopFactory = FopFactory.newInstance(new File("C:/Temp/fop.xconf"));

		// Step 2: Set up output stream.
		// Note: Using BufferedOutputStream for performance reasons (helpful with
		// FileOutputStreams).
		try (OutputStream out = new BufferedOutputStream(new FileOutputStream(new File("C:/Temp/myfile.pdf"))))

		{
			// Step 3: Construct fop with desired output format
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);

			// Step 4: Setup JAXP using identity transformer
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(); // identity transformer

			// Step 5: Setup input and output for XSLT transformation
			// Setup input stream
			Source src = new StreamSource(new File("C:/Temp/myfile.fo"));

			// Resulting SAX events (the generated FO) must be piped through to FOP
			Result res = new SAXResult(fop.getDefaultHandler());

			// Step 6: Start XSLT transformation and FOP processing
			transformer.transform(src, res);
		}
		return null;
	}
}
