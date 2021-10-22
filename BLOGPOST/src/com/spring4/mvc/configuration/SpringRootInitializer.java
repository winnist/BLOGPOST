package com.spring4.mvc.configuration;


import javax.servlet.FilterRegistration;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import com.filter.LoginFilter;


public class SpringRootInitializer implements WebApplicationInitializer{
	
	public void onStartup(ServletContext container) {
		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.register(SpringWebConfig.class);
		ctx.setServletContext(container);
		
		ServletRegistration.Dynamic servlet = container.addServlet("dispatcher", new DispatcherServlet(ctx));

		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");
		
		
//		// Creates the Spring Container shared by all Servlets and Filters
//		container.addListener(new ContextLoaderListener(ctx));
	
		// Set the Encoding of all request and response is UTF-8
		// method2: use FilterRegistration
		FilterRegistration.Dynamic characterEncodingFilter = container.addFilter("CharacterEncodingFilter", CharacterEncodingFilter.class);
		characterEncodingFilter.addMappingForUrlPatterns(null, false, "/*");
		characterEncodingFilter.setInitParameter("encoding", "UTF-8");
		characterEncodingFilter.setInitParameter("forceEncoding", "true");
		
//		FilterRegistration.Dynamic loginFilter = container.addFilter("BlogULoginFilter", LoginFilter.class);
//		loginFilter.addMappingForUrlPatterns(null, false, "/*");
//		
		
		servlet.setMultipartConfig(new MultipartConfigElement(null, 1024*1024*5, 1024*1024*5*5, 1024*1024));

		
	}

}
