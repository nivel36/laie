package ged.web.view.candidate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import org.apache.pdfbox.util.PDFTextStripper;

import ged.web.core.view.AbstractPageBean;

@Named
@ViewScoped
public class AddCurriculumBean extends AbstractPageBean {

	private static final transient Logger logger = Logger.getLogger(AddCurriculumBean.class.getName());

	private static final long serialVersionUID = 2700151546506672587L;

	private String filename;

	private List<String> images;

	private String text;

	public List<String> extractImages(final File file) throws FileNotFoundException, IOException {
		final COSDocument cosDoc = getDocument(file);
		final List<PDXObjectImage> images = getAllImages(cosDoc);
		final List<String> filenames = writeImages(images, file.getName());
		cosDoc.close();
		return filenames;
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

	private List<PDXObjectImage> getAllImages(final COSDocument cosDoc) throws FileNotFoundException, IOException {
		final PDDocument document = new PDDocument(cosDoc);
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

	private COSDocument getDocument(final File file) throws FileNotFoundException, IOException {
		final PDFParser parser = new PDFParser(new FileInputStream(file));
		parser.parse();
		final COSDocument cosDoc = parser.getDocument();

		return cosDoc;
	}

	public String getFilename() {
		return this.filename;
	}

	public List<String> getImages() {
		return this.images;
	}

	public String getText() {
		return this.text;
	}

	@PostConstruct
	public void init() {
		if (this.flash.containsKey("filename")) {
			this.filename = (String) this.flash.get("filename");
		} else {
			throw new IllegalStateException("No filename");
		}
		final String path = "D:\\tmp\\";
		try {
			setImages(extractImages(new File(path + this.filename)));

			final PDDocument pdf = PDDocument.load(new File(path + this.filename));
			final PDFTextStripper stripper = new PDFTextStripper();
			this.text = stripper.getText(pdf);
		} catch (final IOException e) {
			AddCurriculumBean.logger.log(Level.SEVERE, "Can't open file", e);
			addMessage(FacesMessage.SEVERITY_ERROR, "error.unnexpected_error", "error.unnexpected_error");
		}
	}

	public void setFilename(final String filename) {
		this.filename = filename;
	}

	public void setImages(final List<String> images) {
		this.images = images;
	}

	public void setText(final String text) {
		this.text = text;
	}

	private List<String> writeImages(final List<PDXObjectImage> images, final String name) throws IOException {
		int counter = 0;
		final List<String> filenames = new ArrayList<>();
		for (final PDXObjectImage image : images) {
			final StringTokenizer st = new StringTokenizer(this.filename, ".");
			final String filename = st.nextToken() + (++counter);
			final String path = "D:\\tmp\\";
			image.write2file(path + filename);
			filenames.add(filename + ".jpg");
		}
		return filenames;
	}
}
