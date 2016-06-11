package uk.co.mickrisk;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BoothConfiguration {

	@Bean
	public RestOperations restOperations(){
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate;
	}
}
