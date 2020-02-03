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

import ged.ejb.core.Language;
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

	private Language language;

	public void export() throws Exception {
		final Set<TemplateTag> tags = new HashSet<>();
		tags.add(new DateTag(sessionUser.getLocale()));

		final File file = this.documentService.export(this.document, tags);
		Faces.sendFile(file, true);
	}

	public DocumentTemplate getDocument() {
		return this.document;
	}

	public Language getLanguage() {
		return language;
	}

	@PostConstruct
	public void init() {
		this.language = Language.ofCode(this.sessionUser.getLocale().getLanguage());
		searchDocument();
	}

	public void save() {
		this.documentService.save(this.document);
		this.addMessage(FacesMessage.SEVERITY_INFO, "action.save_action_performed", "action.save_action_performed");
	}

	public void searchDocument() {
		final DocumentTemplate documentFromDatabase = this.documentService.findDocumentByName("gdpr", language);
		if (documentFromDatabase == null) {
			this.document = new DocumentTemplate();
			this.document.setName("gdpr");
			this.document.setLanguage(language.getCode());
		} else {
			this.document = documentFromDatabase;
		}
	}

	public void setDocument(final DocumentTemplate document) {
		this.document = document;
	}

	public void setDocumentService(final DocumentTemplateService documentService) {
		this.documentService = documentService;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}
}
