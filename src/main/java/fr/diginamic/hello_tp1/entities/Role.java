package fr.diginamic.hello_tp1.entities;

import java.util.ArrayList;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.ManyToMany;

@Entity 
public class Role implements GrantedAuthority{
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    
    @ManyToMany (mappedBy = "roles")
    private List<Utilisateur> utilisateurs =new ArrayList<>();
    
    public Role(String nom) {
        this.nom = nom;
    }

    


    public Role() {
    }




    @Override
    public @Nullable String getAuthority() {
        return nom;
    }

}
