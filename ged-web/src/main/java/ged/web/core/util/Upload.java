package ged.web.core.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.servlet.http.Part;

public class Upload {
	

	private Part part;
	
	public Part getPart() {
		return part;
	}
	
	public void setPart(Part part) {
		this.part = part;
	}
	
	public String upload() throws Exception {
		InputStream in = null;
		FileOutputStream out = null;
		String fileName = getFileName(part);
		File fileOut = new File("d:/tmp/" + fileName);
		try {
			in = part.getInputStream();
			out = new FileOutputStream(fileOut);
			int read = 0;
			final byte[] bytes = new byte[1024];
			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
		} finally {
			if (out != null) {
				out.close();
			}
			if (in != null) {
				in.close();
			}
		}

		//flash.put("filename", fileName);
		return "addCurriculum";
	}

	// Extract part name from content-disposition header of part part
	private String getFileName(Part part) {
		final String partHeader = part.getHeader("content-disposition");
		for (String content : partHeader.split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim()
						.replace("\"", "");
			}
		}
		// TODO: throw Exception!
		return null;
	}

}
