package uk.co.mickrisk;

import org.springframework.boot.SpringApplication;
import static springfox.documentation.builders.PathSelectors.regex;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
public class BoothApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoothApplication.class, args);
	}

	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.ant("/vote/*"))                          
          .build()
          .apiInfo(apiInfo());                                           
    }

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Voting Booth")
				.description("Remote voting booth application")
				.termsOfServiceUrl("https://github.com/m1ckr1sk/voting_booth")
				.contact("Mike Butt").license("GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007")
				.licenseUrl("https://github.com/m1ckr1sk/voting_booth/blob/master/LICENSE").version("3.0")
				.build();
	}
}
