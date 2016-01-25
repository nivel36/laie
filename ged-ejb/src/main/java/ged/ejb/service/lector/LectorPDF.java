package ged.ejb.service.lector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
	public List<String> extractImages(File file) throws FileNotFoundException, IOException {
		List<PDXObjectImage> images = getAllImages(file);
		List<String> filenames = writeImages(images, file.getName());
		return filenames;
	}

	private List<PDXObjectImage> getAllImages(File file) throws FileNotFoundException, IOException {
		PDDocument document = getDocument(file);
		List<PDXObjectImage> images = new ArrayList<PDXObjectImage>();
		List<PDPage> pages = getAllPages(document);
		for (PDPage page : pages) {
			extractImagesFromPage(images, page);
		}
		return images;
	}

	private List<String> writeImages(List<PDXObjectImage> images, String name) throws IOException {
		int counter = 0;
		List<String> filenames = new ArrayList<String>();
		for (PDXObjectImage image : images) {
			String filename = name + (++counter);
			image.write2file(filename);
			filenames.add(filename);
		}
		return filenames;
	}

	private PDDocument getDocument(File file) throws FileNotFoundException, IOException {
		PDFParser parser = new PDFParser(new FileInputStream(file));
		parser.parse();
		COSDocument cosDoc = parser.getDocument();
		PDDocument pdDoc = new PDDocument(cosDoc);
		return pdDoc;
	}

	@SuppressWarnings("unchecked")
	private List<PDPage> getAllPages(PDDocument document) {
		return document.getDocumentCatalog().getAllPages();
	}

	private void extractImagesFromPage(List<PDXObjectImage> images, PDPage page) {
		PDResources resources = page.getResources();
		Map<String, PDXObject> pDXObjects = resources.getXObjects();
		for (PDXObject pDXObject : pDXObjects.values()) {
			// There are 2 possible values: images or forms. We are looking for
			// images
			if (pDXObject instanceof PDXObjectImage) {
				images.add((PDXObjectImage) pDXObject);
			}
		}
	}
}
