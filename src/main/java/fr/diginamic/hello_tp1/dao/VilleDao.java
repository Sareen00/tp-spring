package fr.diginamic.hello_tp1.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import fr.diginamic.hello_tp1.entities.Ville;
import jakarta.annotation.PostConstruct;

@Repository
public class VilleDao {
    private List<Ville> villes = new ArrayList<>();
    private int nextId = 1;

    @PostConstruct
    public void initData() {
        villes = new ArrayList<>();
        insert(new Ville("Paris", 10000));
        insert(new Ville("Marseille", 9000));
        insert(new Ville("Lyon", 8000));
        insert(new Ville("Toulouse", 7000));
        insert(new Ville("Nice", 6000));
        insert(new Ville("Nantes", 5000));
        insert(new Ville("Montpellier", 4000));
        insert(new Ville("Strasbourg", 3000));
        insert(new Ville("Bordeaux", 2000));
        insert(new Ville("Lille", 1000));
    }


    public List<Ville> findAll() {
        return villes;
    }

    public Ville findById(int id) {
        for (Ville v : villes) {
            if (v.getId() == id) return v;
        }
        return null;
    }

    public Ville findByNom(String nom) {
        for (Ville v : villes) {
            if (v.getNom().equalsIgnoreCase(nom)) return v;
        }
        return null;
    }

    public void insert(Ville ville) {
        ville.setId(nextId++);
        villes.add(ville);
    }

    public boolean update(int id, Ville newVille) {
        Ville v = findById(id);
        if (v == null) return false;
        if (newVille.getNom() != null) v.setNom(newVille.getNom());
        if (newVille.getPopulation() != null) v.setPopulation(newVille.getPopulation());
        return true;
    }

    public boolean delete(int id) {
        return villes.removeIf(v -> v.getId() == id);
    }
    
    public boolean villeExiste(Ville ville) {
        return villes.contains(ville);
    }
    
    public List<Ville> findAllWhoStart(String prefix){
        return villes.stream()
                .filter(v -> v.getNom().toLowerCase().startsWith(prefix.toLowerCase()))
                .toList();
    }

    public List<Ville> extraireParPopulation(Integer min, Integer max) {
        return villes.stream()
                .filter(v -> v.getPopulation() >= min && v.getPopulation() <= max)
                .toList();
    }
    
}