package ftn.kts;

import ftn.kts.exceptions.UniqueConstraintViolationException;
import ftn.kts.service.SubscriptionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.CacheControl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableAsync
public class CulturalOffersApplication implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		// Register resource handler for images
		registry.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/images/")
				.setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
	}

	public static void main(String[] args) throws UniqueConstraintViolationException {
		ApplicationContext applicationContext = SpringApplication.run(CulturalOffersApplication.class, args);

		// Uncomment to populate database with test data
		// Configure by changing private constants in MockDataGenerator class
//		 MockDataGenerator.GenerateMockData(applicationContext);
	}

}
