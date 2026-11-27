package fr.diginamic.hello_tp1.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import fr.diginamic.hello_tp1.entities.Ville;
import fr.diginamic.hello_tp1.exceptions.ExceptionFonctionnelle;
import fr.diginamic.hello_tp1.services.interfaces.IDepartementService;
import fr.diginamic.hello_tp1.services.interfaces.IVilleService;
import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")
public class VilleServiceTest {

    @Autowired 
    private IVilleService villeService;
    @Autowired 
    private IDepartementService departementService;


    @Test
    @Transactional 
    void testDeleteVille() throws ExceptionFonctionnelle {
        Ville ville = villeService.extractVilles().get(0); 
        Integer id = ville.getId();
        villeService.deleteVille(id);
        assertThrows(ExceptionFonctionnelle.class, () -> villeService.extractVille(id));
    }

    @Test
    void testExtractVille() throws ExceptionFonctionnelle {
        Ville ville = villeService.extractVille(1);
        assertTrue(ville.getNom().equals("Montpellier"));
    }

    @Test
    void testExtractVilles() {
        List<Ville> villes = villeService.extractVilles();
        assertFalse(villes.isEmpty());
    }

    @Test
    void testExtractVillesParPrefixe() throws ExceptionFonctionnelle {
        List<Ville> villes = villeService.extractVilles("M");
        assertThat(villes)
            .extracting(Ville::getNom)
            .hasSize(2)
            .contains("Montpellier","Marseille");
    }

    @Test
    void testExtractVillesEntrePopulations() throws ExceptionFonctionnelle {
        List<Ville> villes = villeService.extractVilles(200000, 900000);
        assertThat(villes)
            .extracting(Ville::getNom)
            .hasSize(3)
            .contains("Montpellier","Marseille","Lyon");
    }

    @Test
    void testExtractVillesDepartementEntrePopulations() throws ExceptionFonctionnelle {
        List<Ville> villes = villeService.extractVilles("34",20000,100000);
        assertThat(villes)
            .extracting(Ville::getNom)
            .hasSize(2)
            .contains("Béziers","Sète");
    }

    @Test
    void testExtractVillesDeDep() throws ExceptionFonctionnelle {
        List<Ville> villes = villeService.extractVillesDeDep("34", 2);
        assertThat(villes)
            .extracting(Ville::getNom)
            .hasSize(2)
            .contains("Montpellier","Béziers");
    }



    @Test
    @Transactional 
    void testInsertVille() throws ExceptionFonctionnelle {
        Ville ville = new Ville("Lunel", 26000);
        ville.setDepartement(departementService.extractByCode("34"));

        List<Ville> villes = villeService.insertVille(ville);

        assertThat(villes)
            .hasSize(10)                       
            .extracting(Ville::getNom)
            .contains("Lunel");
    }

    @Test
    void testInsertVilleDejaExistante() {
        Ville ville = new Ville("Montpellier", 1000);
        assertThrows(ExceptionFonctionnelle.class, () -> villeService.insertVille(ville));
    }

    @Test
    @Transactional 
    void testUpdateVille() throws ExceptionFonctionnelle {
        Ville ville = new Ville("Montpellier", 26000);
        villeService.updateVille(1, ville);
        assertTrue(villeService.extractVille(1).getPopulation()==26000);
    }
}
