package es.nivel36.laie.web.view.settings;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.omnifaces.util.Faces;

import es.nivel36.laie.ejb.core.Language;
import es.nivel36.laie.ejb.core.document.DateTag;
import es.nivel36.laie.ejb.core.document.DocumentTemplate;
import es.nivel36.laie.ejb.core.document.DocumentTemplateService;
import es.nivel36.laie.ejb.core.document.TemplateTag;
import es.nivel36.laie.web.core.view.AbstractView;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class EditDocumentView extends AbstractView {

	private static final long serialVersionUID = -5171546281152282168L;

	private DocumentTemplate document;

	@Inject
	private DocumentTemplateService documentService;

	private Language language;

	public void export() throws IOException {
		final Set<TemplateTag> tags = new HashSet<>();
		final Locale locale = this.sessionUser.getLocale();
		tags.add(new DateTag(locale));

		final ByteArrayOutputStream outputStream = this.documentService.export(this.document, tags);
		try (final InputStream is = new ByteArrayInputStream(outputStream.toByteArray())) {
			Faces.sendFile(is, this.document.toString() + ".pdf", true);
		}
	}

	public DocumentTemplate getDocument() {
		return this.document;
	}

	public Language getLanguage() {
		return this.language;
	}

	@PostConstruct
	public void init() {
		this.language = Language.ofCode(this.sessionUser.getLocale().getLanguage());
		this.searchDocument();
	}

	public void save() {
		this.documentService.addDocumentTemplate(document);
		this.addMessage(FacesMessage.SEVERITY_INFO, "action.save_action_performed", "action.save_action_performed");
	}

	public void searchDocument() {
		final DocumentTemplate documentFromDatabase = this.documentService.findDocumentByNameAndLanguage("gdpr",
				this.language);
		if (documentFromDatabase == null) {
			this.document = new DocumentTemplate();
			this.document.setName("gdpr");
			this.document.setLanguage(this.language.getCode());
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

	public void setLanguage(final Language language) {
		this.language = language;
	}
}