package fr.diginamic.hello_tp1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Point d'entrée de l'application Spring Boot de gestion des villes et départements.
 */
@SpringBootApplication
public class HelloTp1Application {

	/**
	 * Démarre l'application Spring Boot.
	 *
	 * @param args arguments de la ligne de commande
	 */
	public static void main(String[] args) {
		SpringApplication.run(HelloTp1Application.class, args);
	}
}