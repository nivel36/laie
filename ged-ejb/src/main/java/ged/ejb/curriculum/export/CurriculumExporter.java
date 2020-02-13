package ged.ejb.curriculum.export;

import ged.ejb.curriculum.Curriculum;
import ged.ejb.curriculum.CurriculumTemplate;

public interface CurriculumExporter {
	
	byte[] export(Curriculum curriculum, CurriculumTemplate template);

}
