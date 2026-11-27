package fr.diginamic.hello_tp1.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity 
public class Utilisateur implements UserDetails{
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable (name = "ROLE_UTIL",
        joinColumns = @JoinColumn (name="id_utilisateur", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn (name = "id_role",referencedColumnName = "id")
    )
    private List<Role> roles = new ArrayList<>();



    

    public Utilisateur() {
    }

    public Utilisateur(String username, String password, Role role) {
        this.username=username;
        this.password =password;
        roles.add(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public @Nullable String getPassword() {
       return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

}
