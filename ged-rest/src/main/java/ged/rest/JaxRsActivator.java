package ged.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.jaxrs.config.BeanConfig;

@ApplicationPath("api")
public class JaxRsActivator extends Application {

	public JaxRsActivator() {
		final BeanConfig beanConfig = new BeanConfig();
		beanConfig.setVersion("1.0.0");
		beanConfig.setSchemes(new String[] { "http" });
		beanConfig.setHost("localhost:8080");
		beanConfig.setBasePath("/rest/api/");
		beanConfig.setResourcePackage("ged.rest");
		beanConfig.setScan(true);
	}
}