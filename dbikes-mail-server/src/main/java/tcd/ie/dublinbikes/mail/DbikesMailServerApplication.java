package tcd.ie.dublinbikes.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.system.ApplicationPidFileWriter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
/**
 * 
 * @author arun
 *
 */

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"tcd.ie.dublinbikes.mail"})
public class DbikesMailServerApplication {

	public static void main(String[] args) {
		
		SpringApplication application = new SpringApplication(DbikesMailServerApplication.class);
		application.addListeners(new ApplicationPidFileWriter("application.pid"));
        application.run(args);
	}
	
	@Bean
    @Primary
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        return builder;
    }
}
