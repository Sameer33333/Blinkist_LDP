package com.training.blinkist;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
public class BlinkistApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlinkistApplication.class, args);

			}
	@Bean
	public ModelMapper modelMapper()
	{

		return new ModelMapper();
	}
	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("public-api")
				.apiInfo(apiInfo()).select().paths(PathSelectors.ant("/api/*")).
				apis(RequestHandlerSelectors.basePackage("com.training.blinkist.controller"))
				.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("BLINKIST API")
				.description("Rest api for blinkist.")
				.version("1.0").build();
	}



}
