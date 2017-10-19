package ged.web.core.view;

import java.io.File;
import org.omnifaces.servlet.FileServlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

@WebServlet("/images/*")
public class ImageServlet extends FileServlet {

	private static final long serialVersionUID = 7820731670232262777L;

	private File folder;

	@Override
	protected File getFile(final HttpServletRequest request) {
		final String pathInfo = request.getPathInfo();

		if (pathInfo == null || pathInfo.isEmpty() || "/".equals(pathInfo)) {
			throw new IllegalArgumentException();
		}

		return new File(this.folder, pathInfo);
	}

	@Override
	public void init() throws ServletException {
		this.folder = new File("c:/Temp/img");
	}
}