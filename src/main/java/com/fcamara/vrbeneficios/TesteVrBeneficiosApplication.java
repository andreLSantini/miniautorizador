package com.fcamara.vrbeneficios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class TesteVrBeneficiosApplication {

	public static void main(String[] args) {
		SpringApplication.run(TesteVrBeneficiosApplication.class, args);
	}

}
