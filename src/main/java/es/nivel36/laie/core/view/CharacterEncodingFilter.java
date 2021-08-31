package es.nivel36.laie.core.view;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebFilter("/*")
public class CharacterEncodingFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(CharacterEncodingFilter.class);

	@Override
	public void destroy() {
		// do nothing
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws ServletException, IOException {
		request.setCharacterEncoding(StandardCharsets.UTF_8.name());
		chain.doFilter(request, response);
	}

	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
		logger.info("UTF-8 Character encoding filter init");
	}
}