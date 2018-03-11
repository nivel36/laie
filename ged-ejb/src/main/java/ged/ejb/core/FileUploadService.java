package ged.ejb.core;

import java.io.File;
import java.io.InputStream;

import ged.ejb.UploadedServerFile;

public interface FileUploadService {

	File getFileFromFileSystem(UploadedServerFile file);

	void removeFileFromFileSystem(String uuid);

	String uploadFile(InputStream inputStream);

	String uploadImage(InputStream inputStream);
}