package ged.web.view.maintenance;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;

import ged.ejb.core.document.DateTag;
import ged.ejb.core.document.DocumentTemplate;
import ged.ejb.core.document.DocumentTemplateService;
import ged.ejb.core.document.TemplateTag;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class GdprMaintenanceView extends AbstractView {

	private static final long serialVersionUID = 1L;

	private DocumentTemplate document;

	@Inject
	private DocumentTemplateService documentService;

	public void export() throws Exception {
		final Set<TemplateTag> tags = new HashSet<>();
		tags.add(new DateTag(sessionUser.getLocale()));
		
		final File file = this.documentService.export(this.document, tags);
		Faces.sendFile(file, true);
	}

	public DocumentTemplate getDocument() {
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

	public void setDocument(final DocumentTemplate document) {
		this.document = document;
	}

	public void setDocumentService(final DocumentTemplateService documentService) {
		this.documentService = documentService;
	}
}
