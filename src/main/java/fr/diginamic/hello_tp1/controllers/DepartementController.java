package fr.diginamic.hello_tp1.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import fr.diginamic.hello_tp1.dto.VilleDto;
import fr.diginamic.hello_tp1.entities.Departement;
import fr.diginamic.hello_tp1.exceptions.ExceptionFonctionnelle;
import fr.diginamic.hello_tp1.services.interfaces.IDepartementService;
import fr.diginamic.hello_tp1.services.interfaces.IVilleService;
import fr.diginamic.hello_tp1.services.utils.PdfExportService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

/**
 * Contrôleur REST exposant les opérations sur les départements (URL /departements).
 */
@RestController
@RequestMapping("/departements")
public class DepartementController implements IDepartementController {

    private final IVilleService villeService;
    private final IDepartementService departementService;
    private final VilleDto villeDto;
    private final PdfExportService pdfExportService;


    DepartementController(IVilleService villeService, VilleDto villeDto, IDepartementService departementService,PdfExportService pdfExportService) {
        this.villeService = villeService;
        this.villeDto = villeDto;
        this.departementService = departementService;
        this.pdfExportService = pdfExportService;
    }


    /**
     * (non-Javadoc)
     * @see IDepartementController#getAllDepartements()
     */
    @Override
    @GetMapping
    public Iterable<Departement> getAllDepartements() {
        return departementService.extractAll();
    }

    /**
     * (non-Javadoc)
     * @see IDepartementController#getDepartementById(int)
     */
    @Override
    @GetMapping("/{id}")
    public Departement getDepartementById(@PathVariable int id) throws ExceptionFonctionnelle{
        return departementService.extractById(id);    
    }

    /**
     * (non-Javadoc)
     * @see IDepartementController#insertDepartement(Departement)
     */
    @Override
    @PostMapping
    public ResponseEntity<String> insertDepartement(@Valid @RequestBody Departement newDepartement)throws ExceptionFonctionnelle{
        departementService.insertDepartement(newDepartement);
        return ResponseEntity.status(HttpStatus.CREATED).body("Departement ajoutée avec succès");
    }

    /**
     * (non-Javadoc)
     * @see IDepartementController#updateDepartement(int, Departement)
     */
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<String> updateDepartement( @PathVariable int id,@Valid @RequestBody Departement newDepartement) throws ExceptionFonctionnelle {
        departementService.updateDepartement(id,newDepartement);
        return ResponseEntity.status(HttpStatus.CREATED).body("Departement modifiée avec succès");
    }

    /**
     * (non-Javadoc)
     * @see IDepartementController#deleteDepartement(int)
     */
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartement(@PathVariable int id) throws ExceptionFonctionnelle {
        departementService.deleteDepartement(id);
        return ResponseEntity.status(HttpStatus.CREATED).body("Departement supprimée avec succès");
    }

    /**
     * (non-Javadoc)
     * @see IDepartementController#getPlusGrandeVille(String, Integer)
     */
    @Override
    @GetMapping("/population/{code}/{nb}")
    public List<VilleDto> getPlusGrandeVille(@PathVariable String code,@PathVariable Integer nb)throws ExceptionFonctionnelle {
        return villeService.extractVillesDeDep(code, nb).stream().map(villeDto::toDto).toList();
    }
    
    /**
     * (non-Javadoc)
     * @see IDepartementController#getVilleEntre(String, Integer, Integer)
     */
    @Override
    @GetMapping("/population/{code}/{min}/{max}")
    public List<VilleDto> getVilleEntre(@PathVariable String code,@PathVariable Integer min,@PathVariable Integer max)throws ExceptionFonctionnelle {
        return villeService.extractVilles(code, min, max).stream().map(villeDto::toDto).toList();
    }



    
    /**
     * Exporte au format PDF la fiche d'un département : code, nom et liste de ses villes.
     *
     * @param code code du département
     * @param response réponse HTTP dans laquelle le PDF est écrit
     * @throws IOException en cas d'erreur d'écriture
     * @throws DocumentException en cas d'erreur de génération du PDF
     * @throws ExceptionFonctionnelle si aucun département ne correspond à ce code
     */
    @GetMapping("/export/pdf/{code}")
    public void ficheDepartement(@PathVariable String code,HttpServletResponse response)throws IOException,DocumentException, ExceptionFonctionnelle {
        response.setContentType("application/pdf");
        String nom = departementService.extractByCode(code).getNom();
        response.setHeader("Content-Disposition", "attachment; filename=\""+ nom + ".pdf\"");

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        pdfExportService.genererPdf(code, document);
    }

}
