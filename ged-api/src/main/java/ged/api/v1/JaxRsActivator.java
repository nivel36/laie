package ged.api.v1;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.jaxrs.config.BeanConfig;

@ApplicationPath("v1")
public class JaxRsActivator extends Application {

	public JaxRsActivator() {
		final BeanConfig beanConfig = new BeanConfig();
		beanConfig.setVersion("1.0.0");
		beanConfig.setSchemes(new String[] { "http" });
		beanConfig.setHost("localhost:8080");
		beanConfig.setBasePath("/api/v1/");
		beanConfig.setResourcePackage("ged.api.v1");
		beanConfig.setScan(true);
	}
}