package ged.web.core.view;

import java.io.File;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.omnifaces.servlet.FileServlet;

import ged.ejb.core.util.ConfigurationProperty;

@WebServlet("/images/*")
public class ImageServlet extends FileServlet {

	private static File folder;

	private static final long serialVersionUID = 7820731670232262777L;

	@Inject
	@ConfigurationProperty("image.directory")
	private String folderPath;

	@Override
	protected File getFile(final HttpServletRequest request) {
		final String pathInfo = request.getPathInfo();
		if ((pathInfo == null) || pathInfo.isEmpty() || "/".equals(pathInfo)) {
			throw new IllegalArgumentException();
		}
		return new File(ImageServlet.folder, pathInfo);
	}

	@Override
	public void init() throws ServletException {
		ImageServlet.folder = new File(this.folderPath);
	}
}