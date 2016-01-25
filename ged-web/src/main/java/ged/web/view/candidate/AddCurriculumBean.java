package ged.web.view.candidate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;
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

import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class AddCurriculumBean extends AbstractBean {

	private static final long serialVersionUID = 2700151546506672587L;

	private String filename;

	private List<String> images;
	
	private String text;

	@PostConstruct
	public void init() {
		if (flash.containsKey("filename")) {
			filename = (String) flash.get("filename");
		} else {
			throw new IllegalStateException("No filename");
		}
		String path = "D:\\tmp\\";
		try {
			setImages(extractImages(new File(path + filename)));
			
			PDDocument pdf = PDDocument.load(new File(path +filename));
			PDFTextStripper stripper = new PDFTextStripper();
			text = stripper.getText(pdf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public List<String> extractImages(File file) throws FileNotFoundException,
			IOException {
		COSDocument cosDoc = getDocument(file);
		List<PDXObjectImage> images = getAllImages(cosDoc);
		List<String> filenames = writeImages(images, file.getName());
		cosDoc.close();
		return filenames;
	}

	private List<PDXObjectImage> getAllImages(COSDocument cosDoc)
			throws FileNotFoundException, IOException {
		PDDocument document = new PDDocument(cosDoc);
		List<PDXObjectImage> images = new ArrayList<PDXObjectImage>();
		List<PDPage> pages = getAllPages(document);
		for (PDPage page : pages) {
			extractImagesFromPage(images, page);
		}
		return images;
	}

	private List<String> writeImages(List<PDXObjectImage> images, String name)
			throws IOException {
		int counter = 0;
		List<String> filenames = new ArrayList<String>();
		for (PDXObjectImage image : images) {
			StringTokenizer st = new StringTokenizer(filename, ".");
			String filename = st.nextToken() + (++counter);
			String path = "D:\\tmp\\";
			image.write2file(path + filename);
			filenames.add(filename+".jpg");
		}
		return filenames;
	}

	private COSDocument getDocument(File file) throws FileNotFoundException,
			IOException {
		PDFParser parser = new PDFParser(new FileInputStream(file));
		parser.parse();
		COSDocument cosDoc = parser.getDocument();

		return cosDoc;
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

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
