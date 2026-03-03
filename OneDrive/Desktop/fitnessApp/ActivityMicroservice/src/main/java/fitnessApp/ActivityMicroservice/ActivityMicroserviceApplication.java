package fitnessApp.ActivityMicroservice;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ActivityMicroserviceApplication {
	@Value("${test.marker:NOT_FOUND}")
	private String marker;

	@PostConstruct
	public void checkMarker() {
		System.out.println("YAML MARKER = " + marker);
	}

	public static void main(String[] args) {
		SpringApplication.run(ActivityMicroserviceApplication.class, args);
	}

}
