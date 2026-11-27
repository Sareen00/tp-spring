package fr.diginamic.hello_tp1.dto;

import org.springframework.stereotype.Component;

/**
 * Identifiants envoyés au endpoint POST /login.
 */
@Component 
public class LoginRequest {
    private String username;
    private String password;

    /**
     * @return nom de l'utilisateur
     */
    public String getUsername() {
        return username;
    }
    /**
     * @param username username à définir
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * @return mot de passe en clair
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param password mot de passe à définir
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
