package ged.ejb.core;

import java.io.File;
import java.io.InputStream;

import ged.ejb.core.file.ServerFile;

public interface FileUploadService {

	File getFileFromFileSystem(ServerFile file);

	void removeFileFromFileSystem(String uuid);

	String uploadFile(InputStream inputStream);

	String uploadImage(InputStream inputStream);
}