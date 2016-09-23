package ged.ejb.core.lector;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

@Stateless
public class LectorPDF implements Lector {

	@Asynchronous
	public List<String> extractImages(final File file) throws IOException {
		final List<PDXObjectImage> images = getAllImages(file);
		return writeImages(images, file.getName());
	}

	private void extractImagesFromPage(final List<PDXObjectImage> images, final PDPage page) {
		final PDResources resources = page.getResources();
		final Map<String, PDXObject> pDXObjects = resources.getXObjects();
		for (final PDXObject pDXObject : pDXObjects.values()) {
			// There are 2 possible values: images or forms. We are looking for
			// images
			if (pDXObject instanceof PDXObjectImage) {
				images.add((PDXObjectImage) pDXObject);
			}
		}
	}

	private List<PDXObjectImage> getAllImages(final File file) throws IOException {
		final PDDocument document = getDocument(file);
		final List<PDXObjectImage> images = new ArrayList<>();
		final List<PDPage> pages = getAllPages(document);
		for (final PDPage page : pages) {
			extractImagesFromPage(images, page);
		}
		return images;
	}

	@SuppressWarnings("unchecked")
	private List<PDPage> getAllPages(final PDDocument document) {
		return document.getDocumentCatalog().getAllPages();
	}

	private PDDocument getDocument(final File file) throws IOException {
		final PDFParser parser = new PDFParser(new FileInputStream(file));
		parser.parse();
		final COSDocument cosDoc = parser.getDocument();
		return new PDDocument(cosDoc);
	}

	private List<String> writeImages(final List<PDXObjectImage> images, final String name) throws IOException {
		int counter = 0;
		final List<String> filenames = new ArrayList<>();
		for (final PDXObjectImage image : images) {
			final String filename = name + (++counter);
			image.write2file(filename);
			filenames.add(filename);
		}
		return filenames;
	}
}
