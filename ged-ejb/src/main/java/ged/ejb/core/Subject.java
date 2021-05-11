package ged.ejb.core;

import ged.ejb.core.file.File;

public interface Subject {
	
	String getUid();
	
	String getEmail();
	
	String getFullName();
	
	File getPicture();
}
