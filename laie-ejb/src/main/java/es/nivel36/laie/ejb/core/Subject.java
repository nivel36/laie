package es.nivel36.laie.ejb.core;

import es.nivel36.laie.ejb.core.file.File;

public interface Subject {
	
	String getUid();
	
	String getEmail();
	
	String getFullName();
	
	File getPicture();
}
