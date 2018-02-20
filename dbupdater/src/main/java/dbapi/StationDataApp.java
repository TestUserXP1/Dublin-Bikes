package dbapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class StationDataApp {

	private static final Logger log = LoggerFactory.getLogger(StationDataApp.class);

	public static void main(String args[]) {
		SpringApplication.run(StationDataApp.class);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			
			Station[] stndata = restTemplate.getForObject(
					"https://api.jcdecaux.com/vls/v1/stations/42?contract=Dublin&apiKey=abf9e3b328f8d505771cd2d3c85ddef36e995451", Station[].class);
			log.info(stndata[0].toString());
		};
	}
}