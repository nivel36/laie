package ged.web.view.candidate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import org.apache.pdfbox.util.PDFTextStripper;

import ged.web.core.util.ConfigurationProperty;
import ged.web.core.util.MessageUtils;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class AddCurriculumBean extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 2700151546506672587L;

	private String filename;

	private List<String> images;

	@Inject
	@ConfigurationProperty("file.directory")
	private String path;

	private String text;

	public List<String> extractImages(final File file) throws IOException {
		final COSDocument cosDoc = getDocument(file);
		final List<PDXObjectImage> imagesFromDoc = getAllImages(cosDoc);
		final List<String> filenames = writeImages(imagesFromDoc);
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

	private List<PDXObjectImage> getAllImages(final COSDocument cosDoc) {
		final PDDocument document = new PDDocument(cosDoc);
		final List<PDXObjectImage> imagesFromDoc = new ArrayList<>();
		final List<PDPage> pages = getAllPages(document);
		for (final PDPage page : pages) {
			extractImagesFromPage(imagesFromDoc, page);
		}
		return imagesFromDoc;
	}

	@SuppressWarnings("unchecked")
	private List<PDPage> getAllPages(final PDDocument document) {
		return document.getDocumentCatalog().getAllPages();
	}

	private COSDocument getDocument(final File file) throws IOException {
		final PDFParser parser = new PDFParser(new FileInputStream(file));
		parser.parse();
		return parser.getDocument();
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
		try {
			setImages(extractImages(new File(this.path + this.filename)));

			final PDDocument pdf = PDDocument.load(new File(this.path + this.filename));
			final PDFTextStripper stripper = new PDFTextStripper();
			this.text = stripper.getText(pdf);
		} catch (final IOException e) {
			AddCurriculumBean.logger.error("Can't open file", e);
			MessageUtils.addErrorMessage("error.unnexpected_error", "error.unnexpected_error");
		}
	}

	public void setFilename(final String filename) {
		this.filename = filename;
	}

	public void setImages(final List<String> images) {
		this.images = images;
	}

	public void setPath(final String path) {
		this.path = path;
	}

	public void setText(final String text) {
		this.text = text;
	}

	private List<String> writeImages(final List<PDXObjectImage> images) throws IOException {
		int counter = 0;
		final List<String> filenames = new ArrayList<>();
		for (final PDXObjectImage image : images) {
			final StringTokenizer st = new StringTokenizer(this.filename, ".");
			final String name = st.nextToken() + (++counter);
			image.write2file(this.path + name);
			filenames.add(name + ".jpg");
		}
		return filenames;
	}
}
