package com.nepc.denodo.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

/**
 * Swagger UI configuration
 *
 * @author Ruben Jimenez
 */

@EnableSwagger2
@Configuration
public class SwaggerConfiguration
{
	private ApiInfo apiInfo()
	{
		return new ApiInfoBuilder().title("NEPC DENODO API").description("NEPC DENODO API").license("")
				.licenseUrl("http://unlicense.org").termsOfServiceUrl("").version("1.0.0")
				.contact(new Contact("", "", "")).build();
	}

	@Bean
	public Docket customImplementation()
	{
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.nepc.denodo.api.endpoint")).build()
				.directModelSubstitute(LocalDate.class, java.sql.Date.class)
				.directModelSubstitute(LocalDateTime.class, java.util.Date.class)
				.directModelSubstitute(OffsetDateTime.class, java.util.Date.class).apiInfo(apiInfo());
	}
}
