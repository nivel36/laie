package es.nivel36.laie.web.core.view;

import java.util.Objects;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import es.nivel36.laie.ejb.core.subject.Subject;
import es.nivel36.laie.ejb.core.subject.SubjectService;
import jakarta.inject.Inject;

@FacesConverter(managed = true, forClass = Subject.class)
public class SubjectConverter implements Converter<Subject> {

	private @Inject SubjectService subjectService;

	@Override
	public Subject getAsObject(FacesContext context, UIComponent component, String email) {
		if (email == null) {
			return null;
		}
		return subjectService.findByEmail(email);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Subject value) {
		if (value == null) {
			return null;
		}
		return value.getEmail();
	}

	public void setSubjectService(SubjectService subjectService) {
		Objects.requireNonNull(subjectService);
		this.subjectService = subjectService;
	}
}
