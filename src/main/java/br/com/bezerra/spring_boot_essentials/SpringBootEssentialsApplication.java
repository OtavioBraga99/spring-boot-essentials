package br.com.bezerra.spring_boot_essentials;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@SpringBootApplication
@EntityScan("br.com.bezerra.spring_boot_essentials.database.model")
public class SpringBootEssentialsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootEssentialsApplication.class,
				args);
	}

}
