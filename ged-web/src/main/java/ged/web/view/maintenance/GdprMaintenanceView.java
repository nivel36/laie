package ged.web.view.maintenance;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;

import ged.ejb.core.document.Document;
import ged.ejb.core.document.DocumentService;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class GdprMaintenanceView extends AbstractView {

	private static final long serialVersionUID = 1L;

	private Document document;

	@Inject
	private DocumentService documentService;

	public void export() throws Exception {
		final File file = this.documentService.export(this.document, "/xsltemplates/xhtml2fo.xsl");
		Faces.sendFile(file, true);
	}

	public Document getDocument() {
		return this.document;
	}

	@PostConstruct
	public void init() {
		this.document = this.documentService.findDocumentByName("gdpr");
	}

	public void save() {
		this.documentService.save(this.document);
		this.addMessage(FacesMessage.SEVERITY_INFO, "action.save_action_performed", "action.save_action_performed");
	}

	public void setDocument(final Document document) {
		this.document = document;
	}

	public void setDocumentService(final DocumentService documentService) {
		this.documentService = documentService;
	}
}
