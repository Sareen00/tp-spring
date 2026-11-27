package fr.diginamic.hello_tp1.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fr.diginamic.hello_tp1.entities.Role;
import fr.diginamic.hello_tp1.entities.Utilisateur;

public class MemoireService implements UserDetailsService{
    @Autowired 
    private PasswordEncoder encoder;

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
    