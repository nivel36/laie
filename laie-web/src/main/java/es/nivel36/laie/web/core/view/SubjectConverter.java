package es.nivel36.laie.web.core.view;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import es.nivel36.laie.ejb.core.subject.Subject;
import es.nivel36.laie.ejb.core.subject.SubjectService;

@FacesConverter(managed = true, forClass = Subject.class)
public class SubjectConverter implements Converter<Subject> {

	private SubjectService subjectService;

	@Override
	public Subject getAsObject(FacesContext context, UIComponent component, String email) {
		return subjectService.findByEmail(email);
	}

	public void setSubjectService(SubjectService subjectService) {
		this.subjectService = subjectService;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Subject value) {
		return value.getEmail();
	}
}
