package es.nivel36.laie.web.core.view;

import java.io.File;

import org.omnifaces.servlet.FileServlet;

import es.nivel36.laie.ejb.core.util.ConfigurationProperty;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;

@WebServlet("/images/*")
public class ImageServlet extends FileServlet {

	private static final long serialVersionUID = 1L;

	@Inject
	@ConfigurationProperty("file.directory")
	private String folderPath;

	@Override
	protected File getFile(final HttpServletRequest request) {
		final String pathInfo = request.getPathInfo();
		if ((pathInfo == null) || pathInfo.isEmpty() || "/".equals(pathInfo)) {
			throw new IllegalArgumentException();
		}
		return new File(this.folderPath, pathInfo);
	}
}