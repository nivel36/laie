package es.nivel36.files;

import java.io.InputStream;

public interface FileService {

	FileDto findByUid(String uid);

	InputStream downloadFile(String uid);

	InputStream downloadTemporalFile(String path);

	FileDto uploadTemporalFile(InputStream inputStream);

	void removeFile(String uid);

	FileDto uploadFile(InputStream inputStream, String filename, boolean publicAccess);

}