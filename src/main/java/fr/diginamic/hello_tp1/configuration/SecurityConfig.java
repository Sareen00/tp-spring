package fr.diginamic.hello_tp1.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import fr.diginamic.hello_tp1.security.JwtAuthenticationFilter;

/**
 * Configuration Spring Security : authentification par JWT, CSP, CORS et sécurité par méthode.
 */
@EnableMethodSecurity (securedEnabled = true)
@Configuration 
public class SecurityConfig {

    /**
     * Définit la chaîne de filtres de sécurité :
     * CSP, CORS, pas de session, /login et Swagger publics, le reste authentifié par JWT.
     *
     * @param http configuration HTTP de Spring Security
     * @param jwtFilter filtre qui authentifie la requête à partir du JWT
     * @return la chaîne de filtres
     * @throws Exception en cas d'erreur de configuration
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtFilter) throws Exception {
        String csp = "script-src 'self' https://cdn.jsdelivr.net;"
                        + "style-src 'self' 'unsafe-inline' https://cdn.jsdelivr.net https://cdnjs.cloudflare.com;"
                        + "img-src 'self' data:;"
                        + "font-src 'self' https://cdnjs.cloudflare.com;";
        http.headers(headers -> headers.contentSecurityPolicy(c -> c.policyDirectives(csp)));
        // http.httpBasic(Customizer.withDefaults());
        http.cors(Customizer.withDefaults());
        http.csrf(c -> c.disable());
        http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests(auth -> auth
            .requestMatchers("/login").permitAll()
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .anyRequest().authenticated());
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    /**
     * Encodeur utilisé pour hasher et vérifier les mots de passe.
     *
     * @return un encodeur BCrypt
     */
    @Bean
    public BCryptPasswordEncoder getEncoder(){
        return new BCryptPasswordEncoder();
    }


    /**
     * Autorise les appels du front Angular (http://localhost:4200).
     *
     * @return la configuration CORS
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
