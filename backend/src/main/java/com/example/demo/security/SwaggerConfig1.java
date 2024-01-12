package com.example.demo.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import java.util.Collections;
import java.util.List;

/**
* Configuration class for Swagger documentation of the Assessment Platform API.
*/

@Configuration
public class SwaggerConfig1 {
	
	/**
     * Configures the Swagger Docket bean to document the API endpoints.
     *
     * @return Configured Swagger Docket bean.
     */
	
	@Bean
	Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
				.securitySchemes(Collections.singletonList(basicAuthScheme()))
				.securityContexts(Collections.singletonList(securityContext())).select()
				.apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
	}
	
	/**
     * Provides API information for Swagger documentation.
     *
     * @return ApiInfo object containing API metadata.
     */
	private ApiInfo apiInfo() {
		return new ApiInfo("Assessment Platform API",
				"Empower your learning experience with our comprehensive Assessment Platform API.Seamlessly manage assessments,quizzes and student submissions for educational journey.",
				"v1",
				"Terms of Service", 
				new Contact("Group16", "https://example.group16.com","group16@ex.com"), 
				null,
				null,
				Collections.emptyList()
				);
	}
	
	/**
     * Configures basic authentication scheme for Swagger documentation.
     *
     * @return BasicAuth object representing basic authentication.
     */
	private SecurityScheme basicAuthScheme() {
		return new BasicAuth("basicAuth");
	}
	
	/**
     * Configures security context for Swagger documentation.
     *
     * @return SecurityContext object defining security settings for the API.
     */
	@SuppressWarnings("deprecation")
	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
	}
	
	/**
     * Provides default authentication settings for Swagger documentation.
     *
     * @return List of SecurityReference objects defining authentication scopes.
     */
	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		SecurityReference reference = SecurityReference.builder().reference("basicAuth")
				.scopes(new AuthorizationScope[] { authorizationScope }).build();
		return Collections.singletonList(reference);
	}
}