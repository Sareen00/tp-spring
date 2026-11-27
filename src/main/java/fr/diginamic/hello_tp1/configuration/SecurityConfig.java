package fr.diginamic.hello_tp1.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration 
public class SecurityConfig {

    @Bean 
    SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http.httpBasic(Customizer.withDefaults());
        http.authorizeHttpRequests(auth -> auth
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/**").hasAnyRole("USER", "ADMIN")
            .anyRequest().hasRole("ADMIN"));
            
        http.csrf(c -> c.disable());
        return http.build();
    }

    /**
     * 
     */
    @Bean
    public BCryptPasswordEncoder getEncoder(){
        return new BCryptPasswordEncoder();
    }

}
