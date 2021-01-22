package ftn.kts;

import ftn.kts.exceptions.UniqueConstraintViolationException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CulturalOffersApplication {

	public static void main(String[] args) throws UniqueConstraintViolationException {
		ApplicationContext applicationContext = SpringApplication.run(CulturalOffersApplication.class, args);

		// Uncomment to populate database with test data
		// Configure by changing private constants in MockDataGenerator class
//		 MockDataGenerator.GenerateMockData(applicationContext);
	}

}
