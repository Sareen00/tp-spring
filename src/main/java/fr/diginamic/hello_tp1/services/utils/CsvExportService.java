package fr.diginamic.hello_tp1.services.utils;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itextpdf.text.DocumentException;

import fr.diginamic.hello_tp1.entities.Ville;
import fr.diginamic.hello_tp1.exceptions.ExceptionFonctionnelle;
import fr.diginamic.hello_tp1.services.interfaces.IVilleService;

@Service 
public class CsvExportService {
    private final IVilleService villeService;

    public CsvExportService(IVilleService villeService) {
        this.villeService = villeService;
    }

    public void genererCsvVilles(Integer min, Writer writer) throws DocumentException, IOException, ExceptionFonctionnelle{
        List<Ville> villes = villeService.extractVilles(min, Integer.MAX_VALUE);
        writer.write("Nom;Population;Code département;Nom département\n");
        for (Ville v : villeService.extractVilles(min, Integer.MAX_VALUE)) {
            writer.write(v.getNom() + ";" + v.getPopulation() + ";" + v.getDepartement().getCode() + ";" + v.getDepartement().getNom() + "\n");
        }


    }
}
