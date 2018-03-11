package ged.web.core.view;

import java.io.File;

import org.primefaces.model.UploadedFile;

import ged.ejb.UploadedServerFile;

public interface FileUploadService {

	File getFileFromFileSystem(UploadedServerFile file);

	void removeFileFromFileSystem(String uuid);

	String uploadFile(UploadedFile file);

	String uploadImage(UploadedFile file);
}