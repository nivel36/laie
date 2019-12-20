package ged.web.view.maintenance;

import java.io.File;
import java.io.IOException;

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

	public Document getDocument() {
		return document;
	}

	@PostConstruct
	public void init() {
		this.document = documentService.findDocumentByName("gdpr");
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}

	public void save() {
		this.documentService.save(document);
		this.addMessage(FacesMessage.SEVERITY_INFO, "action.save_action_performed", "action.save_action_performed");
	}
	
	public void export() throws Exception {
		File file = this.documentService.export();
		Faces.sendFile(file, true);
	}
}
