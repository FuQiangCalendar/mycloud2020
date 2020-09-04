package com.atguigu.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2{
	@Bean
	public Docket createRestApi(){

		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.atguigu.springcloud.controller"))
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo apiInfo(){
	
		return new ApiInfoBuilder()
	
				.title("Spring Boot中使用Swagger2构建RESTful APIs")
	
				.description("更多Spring Boot相关文章请关注：广告链接")
	
				.termsOfServiceUrl(" 广告链接  url")
	
				.contact("支付模块")
	
				.version("2.0")
				.build();
	}

}
