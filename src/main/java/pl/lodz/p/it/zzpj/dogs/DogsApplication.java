package pl.lodz.p.it.zzpj.dogs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DogsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DogsApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplateBean() {
		return new RestTemplate();
	}
}
