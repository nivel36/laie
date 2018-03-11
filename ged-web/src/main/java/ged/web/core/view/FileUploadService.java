package ged.web.core.view;

import java.io.File;
import java.io.IOException;

import org.primefaces.model.UploadedFile;

import ged.ejb.UploadedServerFile;

public interface FileUploadService {

	File getFileFromFileSystem(UploadedServerFile file) throws IOException;

	void removeFileFromFileSystem(String uuid) throws IOException;

	String uploadFile(UploadedFile file) throws IOException;

	String uploadImage(UploadedFile file) throws IOException;

}