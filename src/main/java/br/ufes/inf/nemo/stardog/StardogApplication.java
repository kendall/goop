package br.ufes.inf.nemo.stardog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:applicationContext.xml")
public class StardogApplication {

	public static void main(String[] args) {
		SpringApplication.run(StardogApplication.class, args);
	}

}

