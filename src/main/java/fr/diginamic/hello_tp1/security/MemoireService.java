package fr.diginamic.hello_tp1.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import fr.diginamic.hello_tp1.entities.Role;
import fr.diginamic.hello_tp1.entities.Utilisateur;

/**
 * Ancienne implémentation en mémoire de UserDetailsService (TP2), remplacée par JpaUserDetailsService.
 */
public class MemoireService implements UserDetailsService{
    private final PasswordEncoder encoder;

    /**
     * @param encoder encodeur des mots de passe
     */
    MemoireService(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals("user")) {
            return new Utilisateur("user", encoder.encode("user123"), new Role("ROLE_USER"));
        }
        else if (username.equals("admin")) {
            return new Utilisateur("admin", encoder.encode("admin123"), new Role("ROLE_ADMIN"));
        }
        throw new UsernameNotFoundException("mdp ou idenifiant incorect");
    }
}
    