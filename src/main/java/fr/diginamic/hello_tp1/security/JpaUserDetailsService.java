package fr.diginamic.hello_tp1.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.diginamic.hello_tp1.repositories.UtilisateurRepository;

/**
 * Charge les utilisateurs depuis la base pour Spring Security.
 */
@Service 
public class JpaUserDetailsService implements UserDetailsService{
    private final UtilisateurRepository utilisateurRepository;

    /**
     * @param utilisateurRepository repository des utilisateurs
     */
    JpaUserDetailsService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails utilisateur = utilisateurRepository.findByUsername(username);
        if (utilisateur == null) {
            throw new UsernameNotFoundException("Utilisateur introuvable");
        }
        return utilisateur;
    }

}
