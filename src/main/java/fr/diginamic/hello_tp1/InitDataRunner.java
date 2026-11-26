package fr.diginamic.hello_tp1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import fr.diginamic.hello_tp1.services.interfaces.IDepartementService;

/**
 * Lance l'initialisation des données au démarrage de l'application.
 */
@Component 
public class InitDataRunner implements CommandLineRunner{

    private final IDepartementService departementService;

    InitDataRunner(IDepartementService departementService) {
        this.departementService = departementService;
    }

    /**
     * Appelé automatiquement par Spring Boot une fois l'application démarrée.
     * Met à jour le nom des départements depuis l'API geo.api.gouv.fr si application.init vaut true.
     *
     * @param args arguments de la ligne de commande
     * @throws Exception en cas d'erreur pendant l'initialisation
     */
    @Override
    public void run(String... args) throws Exception {
        departementService.initData();
    }

}
