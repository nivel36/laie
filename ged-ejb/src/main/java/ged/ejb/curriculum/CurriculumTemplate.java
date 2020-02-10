package ged.ejb.curriculum;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.sun.istack.NotNull;

import ged.ejb.core.i18n.I18nString;
import ged.ejb.core.model.AbstractEntity;

@Entity
public class CurriculumTemplate extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Column(nullable = false)
	private String screenshootPath;

	@NotNull
	@Column(nullable = false)
	private String css;
	
	@NotNull
	private I18nString title;
}
