package com.MailService.EmailApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@EnableSwagger2
public class EmailApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailApplication.class, args);

	}
	@Bean
	public Docket get(){
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
				.paths(PathSelectors.any())
				.build().apiInfo(createApiInfo());
	}
	private ApiInfo createApiInfo(){
		return new ApiInfo("Email sender to all emails in Database",
				"Email tool",
				"1.0",
				"http://jakub.kor.pl",
				new Contact("Jakub","http://jakub.kor.pl","kordulasinski.jakub@icloud.com"),
				"My own license",
				"http://jakub.kor.pl",
				Collections.emptyList()
				);
	}
}
