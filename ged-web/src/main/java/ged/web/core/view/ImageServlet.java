package ged.web.core.view;

import java.io.File;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.omnifaces.servlet.FileServlet;

import ged.ejb.core.util.ConfigurationProperty;

@WebServlet("/images/*")
public class ImageServlet extends FileServlet {

	private static final long serialVersionUID = 7820731670232262777L;

	@Inject
	@ConfigurationProperty("image.directory")
	private String folderPath;

	@Override
	protected File getFile(final HttpServletRequest request) {
		final String pathInfo = request.getPathInfo();
		if (!pathInfo.matches("[a-zA-Z0-9-#-/]++")) {
			throw new IllegalArgumentException();
		}
		if ((pathInfo == null) || pathInfo.isEmpty() || "/".equals(pathInfo)) {
			throw new IllegalArgumentException();
		}
		return new File(this.folderPath, pathInfo);
	}
}