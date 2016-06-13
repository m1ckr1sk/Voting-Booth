package uk.co.mickrisk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class VotersApplication {

	public static void main(String[] args) {
		SpringApplication.run(VotersApplication.class, args);
	}
}
