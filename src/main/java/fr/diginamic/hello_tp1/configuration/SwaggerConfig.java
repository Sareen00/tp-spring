package fr.diginamic.hello_tp1.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

/**
 * Configuration de la documentation Swagger (OpenAPI) de l'API.
 */
@Configuration
public class SwaggerConfig {
    /**
     * Définit les informations générales affichées dans Swagger UI.
     *
     * @return la configuration OpenAPI
     */
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
        .info(new Info()
            .title("API Recensement")
            .version("1.0")
            .description("Cette API fournit des données de recensement de population pour la France.")
            .termsOfService("OPEN DATA")
            .contact(new Contact().name("Julien").email("jmilhauvillar@gmail.com").url("http://localhost:8080/swagger-ui/index.html"))
            .license(new License().name("Licence name").url("url licence")));
    }
}
