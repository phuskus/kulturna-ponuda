package ftn.kts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CulturalOffersApplication {

	public static void main(String[] args) {
		SpringApplication.run(CulturalOffersApplication.class, args);
	}

}
