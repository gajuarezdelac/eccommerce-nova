/**
 * 
 */
package com.course.altanto.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author gabriel.juarez
 *
 */

@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig {

	 @Bean
	    public Docket api() {
	        return new Docket(DocumentationType.SWAGGER_2)
	                .select()
	                .apis(RequestHandlerSelectors.basePackage("com.course.altanto.controller"))
	                .paths(PathSelectors.any())
	                .build().apiInfo(metaData());
	    }

	    private ApiInfo metaData() {
	        return new ApiInfoBuilder()
	                .title("Macropay - Documentación API | Portal de servicios SAP")
	                .description("Macropay SAP 2021")
	                .version("1.1.0")
	                .license("Apache 2.0")
	                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
	                .contact(new Contact("Elvis Novelo", "SAP","elvis.novelo@grupomacro.mx"))
	                .build();
	    }
	    
	    @Bean
	    public WebMvcConfigurer webMvcConfigurer()
	    {
	        return new WebMvcConfigurer()
	        {
	            @Override
	            public void addResourceHandlers( ResourceHandlerRegistry registry )
	            {
	                registry.addResourceHandler( "swagger-ui.html" ).addResourceLocations( "classpath:/META-INF/resources/" );
	                registry.addResourceHandler( "/webjars/**" ).addResourceLocations( "classpath:/META-INF/resources/webjars/" );
	            }
	        };
	    }
	
	

}

