package uk.co.mickrisk;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BoothConfiguration {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
