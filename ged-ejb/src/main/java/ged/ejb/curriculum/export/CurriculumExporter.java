package ged.ejb.curriculum.export;

import java.io.File;

import ged.ejb.curriculum.Curriculum;
import ged.ejb.curriculum.CurriculumTemplate;

public interface CurriculumExporter {

	File export(Curriculum curriculum, CurriculumTemplate template);

}
