package es.nivel36.laie.ejb.curriculum.export;

import java.io.File;

import es.nivel36.laie.ejb.curriculum.Curriculum;
import es.nivel36.laie.ejb.curriculum.CurriculumTemplate;

public interface CurriculumExporter {

	File export(Curriculum curriculum, CurriculumTemplate template);

}
