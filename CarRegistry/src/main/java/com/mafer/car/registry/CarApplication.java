package com.mafer.car.registry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class CarApplication {

	@Value("${server.port}")
	public String port;

	@PostConstruct
	public void postconstruct() {
		log.info("The app is running in port -> " + port);
	}


	public static void main(String[] args) {

		log.info("Welcome to CarRegistry Application");
		SpringApplication.run(CarApplication.class, args);
	}

}
