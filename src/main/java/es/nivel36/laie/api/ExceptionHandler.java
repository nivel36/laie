package es.nivel36.laie.api;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {

	public Response toResponse(Exception ex) {
		return Response.status(500).build();
	}
}