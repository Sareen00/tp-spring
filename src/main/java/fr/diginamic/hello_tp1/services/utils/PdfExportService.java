package fr.diginamic.hello_tp1.services.utils;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import fr.diginamic.hello_tp1.entities.Departement;
import fr.diginamic.hello_tp1.entities.Ville;
import fr.diginamic.hello_tp1.exceptions.ExceptionFonctionnelle;
import fr.diginamic.hello_tp1.services.interfaces.IDepartementService;

/**
 * Service de génération des exports PDF.
 */
@Service 
public class PdfExportService {
    
    private IDepartementService departementService;
    
    /**
     * @param departementService service des départements
     */
    public PdfExportService(IDepartementService departementService) {
        this.departementService = departementService;
    }

    /**
     * Écrit dans le document la fiche d'un département : code, nom et tableau de ses villes.
     *
     * @param code code du département
     * @param document document PDF à remplir
     * @throws DocumentException en cas d'erreur de génération du PDF
     * @throws IOException en cas d'erreur de police ou d'écriture
     * @throws ExceptionFonctionnelle si aucun département ne correspond à ce code
     */
    public void genererPdf(String code, Document document) throws DocumentException, IOException, ExceptionFonctionnelle{
        Departement departement = departementService.extractByCode(code);
        document.open();
        document.addTitle(departement.getNom());
        BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);

        document.add(new Paragraph("Code du département : " + departement.getCode(), new Font(baseFont,16.0f, Font.BOLD, new BaseColor(0,0,0))));
        document.add(new Paragraph("Nom du département : " + departement.getNom(), new Font(baseFont,16.0f, Font.BOLD, new BaseColor(0,0,0))));
        document.add(new Paragraph("Liste des villes : ", new Font(baseFont,16.0f, Font.BOLD, new BaseColor(0,0,0))));

        Font entete = new Font(baseFont, 12f, Font.BOLD, BaseColor.WHITE);
        Font normal = new Font(baseFont, 12f, Font.NORMAL, BaseColor.BLACK);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);        
        table.setWidths(new float[]{3f, 1f});      
        table.setSpacingBefore(10f);

        // En-têtes
        for (String titre : new String[]{"Nom", "Population"}) {
            PdfPCell cell = new PdfPCell(new Phrase(titre, entete));
            cell.setBackgroundColor(BaseColor.DARK_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5f);
            table.addCell(cell);
        }
        table.setHeaderRows(1);                      

        for (Ville ville : departement.getVilles()) {
            table.addCell(new PdfPCell(new Phrase(ville.getNom(), normal)));

            PdfPCell pop = new PdfPCell(new Phrase(String.valueOf(ville.getPopulation()), normal));
            pop.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(pop);
        }

        document.add(table);
        document.close();
    }
}
