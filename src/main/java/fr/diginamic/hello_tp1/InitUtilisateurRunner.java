package fr.diginamic.hello_tp1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import fr.diginamic.hello_tp1.entities.Role;
import fr.diginamic.hello_tp1.entities.Utilisateur;
import fr.diginamic.hello_tp1.repositories.RoleRepository;
import fr.diginamic.hello_tp1.repositories.UtilisateurRepository;

/**
 * Crée au démarrage les rôles USER et ADMIN ainsi qu'un utilisateur pour chacun,
 * si la base ne les contient pas encore.
 */
@Component
public class InitUtilisateurRunner implements CommandLineRunner {

    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    /**
     * @param roleRepository repository des rôles
     * @param encoder encodeur utilisé pour hasher les mots de passe
     * @param utilisateurRepository repository des utilisateurs
     */
    InitUtilisateurRunner(RoleRepository roleRepository, PasswordEncoder encoder, UtilisateurRepository utilisateurRepository) {
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.utilisateurRepository = utilisateurRepository;
    }

    /**
     * Crée les rôles et les utilisateurs user / admin s'ils n'existent pas.
     *
     * @param args arguments de la ligne de commande
     * @throws Exception en cas d'erreur pendant l'initialisation
     */
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