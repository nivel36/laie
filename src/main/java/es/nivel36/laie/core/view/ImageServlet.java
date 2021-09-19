package es.nivel36.laie.core.view;

import java.io.File;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.omnifaces.servlet.FileServlet;

@WebServlet("/images/*")
public class ImageServlet extends FileServlet {

	private static final long serialVersionUID = 1L;

	@Inject
	@ConfigProperty(name = "file.directory")
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