package fr.diginamic.hello_tp1.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;

import fr.diginamic.hello_tp1.dto.VilleDto;
import fr.diginamic.hello_tp1.entities.Departement;
import fr.diginamic.hello_tp1.entities.Ville;
import fr.diginamic.hello_tp1.exceptions.ExceptionFonctionnelle;
import fr.diginamic.hello_tp1.services.interfaces.IDepartementService;
import fr.diginamic.hello_tp1.services.interfaces.IVilleService;
import fr.diginamic.hello_tp1.services.utils.CsvExportService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * Contrôleur REST exposant les opérations sur les villes (URL /villes).
 * Les données échangées avec l'extérieur sont des {@link VilleDto}.
 */
@RestController
@RequestMapping("/villes")
public class VilleController implements IVilleController {

    private final IVilleService villeService;
    private final IDepartementService departementService;
    private final VilleDto villeDto;
    private final CsvExportService csvExportService;

    /**
     * @param villeService service des villes
     * @param villeDto convertisseur Ville / VilleDto
     * @param departementService service des départements
     * @param csvExportService service de génération des CSV
     */
    VilleController(IVilleService villeService, VilleDto villeDto,IDepartementService departementService, CsvExportService csvExportService) {
        this.villeService = villeService;
        this.villeDto = villeDto;
        this.departementService = departementService;
        this.csvExportService = csvExportService;
    }

    

    @Override
    @Secured ({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping
    public List<VilleDto> getAllVilles() {
        return villeService.extractVilles().stream().map(villeDto::toDto).toList();
    }


    @Override
    @Secured ({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("/paginate/{page}")
    public List<VilleDto> extraireAllPaginateVilles(@PathVariable Integer page, @RequestParam(defaultValue = "10") int size) {
        return villeService.extractVillesPaginate(page, size).stream().map(villeDto::toDto).toList();
    }


    @Override
    @Secured ({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("/{id}")
    public VilleDto getVilleById(@Parameter(description = "identifiant de la ville à récupérer",example = "34", required = true) @PathVariable int id) throws ExceptionFonctionnelle{
        return villeDto.toDto(villeService.extractVille(id));    
    }



    @Override
    @Secured ({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<VilleDto> insertVille(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "ville à créer (avec idDepartement ou codeDepartement)", required = true) @Valid @RequestBody VilleDto newVilleDto)throws ExceptionFonctionnelle{
        Departement dep = departementService.extractByIdOrCode(newVilleDto.getIdDepartement(), newVilleDto.getCodeDepartement());
        if (dep == null) {
            if (newVilleDto.getCodeDepartement() == null) throw new ExceptionFonctionnelle("Département inconnue");
            dep = departementService.creerDepartement(newVilleDto.getCodeDepartement());
        }
        Ville ville = villeDto.toBean(newVilleDto);
        ville.setDepartement(dep);
        villeService.insertVille(ville);
        return ResponseEntity.status(HttpStatus.CREATED).body(newVilleDto);
    }


    @Override
    @Secured ({"ROLE_ADMIN"})
    @PostMapping("/liste")
    public ResponseEntity<String> insertVilles(@RequestBody List<@Valid VilleDto> dtos) throws ExceptionFonctionnelle {
        for (VilleDto dto : dtos) {
            insertVille(dto);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(dtos.size() + " villes ajoutées");
    }


    @Override
    @Secured ({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<List<VilleDto>> updateVille(@Parameter(description = "identifiant de la ville à modifier", example = "34", required = true) @PathVariable int id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody (description = "nouvelles valeurs de la ville", required = true) @Valid @RequestBody VilleDto newVilleDto) throws ExceptionFonctionnelle {
        villeService.updateVille(id, villeDto.toBean(newVilleDto));
        return ResponseEntity.ok(villeService.extractVilles().stream().map(villeDto::toDto).toList());
    }


    @Override
    @Secured ({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<List<VilleDto>> deleteVille(@Parameter(description = "identifiant de la ville à supprimer", example = "34", required = true) @PathVariable int id) throws ExceptionFonctionnelle {
        villeService.deleteVille(id);
        return ResponseEntity.ok(villeService.extractVilles().stream().map(villeDto::toDto).toList());
    }



    @Override
    @Secured ({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("/contiennent/{prefix}")
    public List<VilleDto> getVilleCommencePar(@PathVariable String prefix)throws ExceptionFonctionnelle {
        return villeService.extractVilles(prefix).stream().map(villeDto::toDto).toList();
    }
    

    @Override
    @Secured ({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("/population/{min}")
    public List<VilleDto> getVillePopSuperieur(@PathVariable Integer min)throws ExceptionFonctionnelle {
        return villeService.extractVilles(min, Integer.MAX_VALUE).stream().map(villeDto::toDto).toList();
    }
    

 
    @Override
    @Secured ({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("/population/{min}/{max}")
    public List<VilleDto> getVilleEntre(@PathVariable Integer min,@PathVariable Integer max)throws ExceptionFonctionnelle {
        return villeService.extractVilles(min, max).stream().map(villeDto::toDto).toList();
    }

    /**
     * Exporte au format CSV les villes dont la population est supérieure à un minimum.
     * Colonnes : nom, population, code département, nom département.
     *
     * @param min population minimum
     * @param response réponse HTTP dans laquelle le CSV est écrit
     * @throws IOException en cas d'erreur d'écriture
     * @throws DocumentException non utilisée pour le CSV
     * @throws ExceptionFonctionnelle si aucune ville n'est trouvée
     */
    @Secured ({"ROLE_ADMIN"})
    @GetMapping("export/csv/{min}")
    public void exportVilles(@PathVariable Integer min,HttpServletResponse response)throws IOException,DocumentException, ExceptionFonctionnelle {
        
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"villes.csv\"");
        response.getWriter().write('\uFEFF');
        csvExportService.genererCsvVilles(min, response.getWriter());
        response.flushBuffer();
    }

    
}
