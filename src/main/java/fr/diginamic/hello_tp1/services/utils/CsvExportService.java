package fr.diginamic.hello_tp1.services.utils;

import java.io.IOException;
import java.io.Writer;

import org.springframework.stereotype.Service;

import com.itextpdf.text.DocumentException;

import fr.diginamic.hello_tp1.entities.Ville;
import fr.diginamic.hello_tp1.exceptions.ExceptionFonctionnelle;
import fr.diginamic.hello_tp1.services.interfaces.IVilleService;

/**
 * Service de génération des exports CSV.
 */
@Service 
public class CsvExportService {
    private final IVilleService villeService;

    /**
     * @param villeService service des villes
     */
    public CsvExportService(IVilleService villeService) {
        this.villeService = villeService;
    }

    /**
     * Écrit au format CSV les villes dont la population est supérieure à un minimum.
     *
     * @param min population minimum
     * @param writer flux dans lequel le CSV est écrit
     * @throws DocumentException non utilisée pour le CSV
     * @throws IOException en cas d'erreur d'écriture
     * @throws ExceptionFonctionnelle si aucune ville n'est trouvée
     */
    public void genererCsvVilles(Integer min, Writer writer) throws DocumentException, IOException, ExceptionFonctionnelle{
        writer.write("Nom;Population;Code département;Nom département\n");
        for (Ville v : villeService.extractVilles(min, Integer.MAX_VALUE)) {
            writer.write(v.getNom() + ";" + v.getPopulation() + ";" + v.getDepartement().getCode() + ";" + v.getDepartement().getNom() + "\n");
        }
    }
}
