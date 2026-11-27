package fr.diginamic.hello_tp1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import fr.diginamic.hello_tp1.entities.Role;
import fr.diginamic.hello_tp1.entities.Utilisateur;
import fr.diginamic.hello_tp1.repositories.RoleRepository;
import fr.diginamic.hello_tp1.repositories.UtilisateurRepository;

@Component
public class InitUtilisateurRunner implements CommandLineRunner {

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        // déjà initialisé → on ne recrée pas
        if (utilisateurRepository.findByUsername("admin") != null) {
            return;
        }

        Role roleUser = roleRepository.save(new Role("ROLE_USER"));
        Role roleAdmin = roleRepository.save(new Role("ROLE_ADMIN"));
        utilisateurRepository.save(new Utilisateur("user", encoder.encode("user123"), roleUser));
        utilisateurRepository.save(new Utilisateur("admin", encoder.encode("admin123"), roleAdmin));
    }
}