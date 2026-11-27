package fr.diginamic.hello_tp1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.hello_tp1.dto.LoginRequest;
import fr.diginamic.hello_tp1.security.JwtUtil;

/**
 * Contrôleur de connexion : vérifie les identifiants et renvoie un JWT.
 */
@RestController
public class LoginController {
    @Autowired
    private AuthenticationConfiguration config;
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Vérifie le username et le mot de passe, puis génère un JWT pour l'utilisateur.
     *
     * @param req identifiants de connexion
     * @return le JWT à envoyer ensuite dans le header Authorization: Bearer
     * @throws Exception si les identifiants sont incorrects
     */
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest req) throws Exception {
        config.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        return jwtUtil.generateToken(req.getUsername());
    }
}
