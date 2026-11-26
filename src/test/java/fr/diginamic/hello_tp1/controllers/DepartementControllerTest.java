package fr.diginamic.hello_tp1.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import fr.diginamic.hello_tp1.dto.VilleDto;
import fr.diginamic.hello_tp1.entities.Departement;
import fr.diginamic.hello_tp1.entities.Ville;
import fr.diginamic.hello_tp1.exceptions.ExceptionFonctionnelle;
import fr.diginamic.hello_tp1.services.interfaces.IDepartementService;
import fr.diginamic.hello_tp1.services.interfaces.IVilleService;
import fr.diginamic.hello_tp1.services.utils.PdfExportService;

/**
 * Tests des routes de DepartementController avec MockMvc (services mockés).
 */
@WebMvcTest(DepartementController.class)
public class DepartementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IDepartementService departementService;

    @MockitoBean
    private IVilleService villeService;

    @MockitoBean
    private VilleDto villeDto;

    @MockitoBean
    private PdfExportService pdfExportService;

    private Departement herault;

    @BeforeEach
    public void before() {
        herault = new Departement();
        herault.setId(1);
        herault.setCode("34");
        herault.setNom("Hérault");
    }

    // ---------- GET /departements ----------

    @Test
    void testGetAllDepartements() throws Exception {
        when(departementService.extractAll()).thenReturn(List.of(herault));

        mockMvc.perform(get("/departements"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].nom").value("Hérault"));
    }

    @Test
    void testGetAllDepartementsVide() throws Exception {
        when(departementService.extractAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/departements"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void testGetDepartementById() throws Exception {
        when(departementService.extractById(1)).thenReturn(herault);

        mockMvc.perform(get("/departements/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("34"))
            .andExpect(jsonPath("$.nom").value("Hérault"));
    }

    @Test
    void testGetDepartementByIdInconnu() throws Exception {
        when(departementService.extractById(99)).thenThrow(new ExceptionFonctionnelle("Aucun département avec l'id 99"));

        mockMvc.perform(get("/departements/99"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Aucun département avec l'id 99"));
    }


    @Test
    void testInsertDepartement() throws Exception {
        String json = "{\"code\":\"30\",\"nom\":\"Gard\"}";

        mockMvc.perform(post("/departements").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());
    }

    @Test
    void testInsertDepartementDejaExistant() throws Exception {
        doThrow(new ExceptionFonctionnelle("Un département avec le code 34 existe déjà"))
            .when(departementService).insertDepartement(any(Departement.class));
        String json = "{\"code\":\"34\",\"nom\":\"Hérault\"}";

        mockMvc.perform(post("/departements").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isBadRequest());
    }

    @Test
    void testInsertDepartementNomTropCourt() throws Exception {
        // @Size(min = 2) sur le nom : "A" doit être refusé
        String json = "{\"code\":\"30\",\"nom\":\"A\"}";

        mockMvc.perform(post("/departements").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isBadRequest());
    }


    @Test
    void testUpdateDepartement() throws Exception {
        String json = "{\"code\":\"34\",\"nom\":\"Herault modifié\"}";

        mockMvc.perform(put("/departements/1").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());
    }

    @Test
    void testUpdateDepartementInconnu() throws Exception {
        doThrow(new ExceptionFonctionnelle("Aucune Departement avec l'id 99"))
            .when(departementService).updateDepartement(eq(99), any(Departement.class));
        String json = "{\"code\":\"34\",\"nom\":\"Hérault\"}";

        mockMvc.perform(put("/departements/99").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isBadRequest());
    }


    @Test
    void testDeleteDepartement() throws Exception {
        mockMvc.perform(delete("/departements/1"))
            .andExpect(status().isCreated());
    }

    @Test
    void testDeleteDepartementInconnu() throws Exception {
        doThrow(new ExceptionFonctionnelle("Aucun département avec l'id 99"))
            .when(departementService).deleteDepartement(99);

        mockMvc.perform(delete("/departements/99"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void testGetPlusGrandeVille() throws Exception {
        Ville montpellier = new Ville("Montpellier", 285000);
        VilleDto dto = new VilleDto();
        dto.setNom("Montpellier");
        dto.setPopulation(285000);
        when(villeService.extractVillesDeDep("34", 1)).thenReturn(List.of(montpellier));
        when(villeDto.toDto(montpellier)).thenReturn(dto);

        mockMvc.perform(get("/departements/population/34/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].nom").value("Montpellier"));
    }

    @Test
    void testGetPlusGrandeVilleDepInconnu() throws Exception {
        when(villeService.extractVillesDeDep("00", 1)).thenThrow(new ExceptionFonctionnelle("Aucune ville dans le département 00"));

        mockMvc.perform(get("/departements/population/00/1"))
            .andExpect(status().isBadRequest());
    }


    @Test
    void testGetVilleEntre() throws Exception {
        Ville beziers = new Ville("Béziers", 78000);
        VilleDto dto = new VilleDto();
        dto.setNom("Béziers");
        dto.setPopulation(78000);
        when(villeService.extractVilles("34", 50000, 100000)).thenReturn(List.of(beziers));
        when(villeDto.toDto(beziers)).thenReturn(dto);

        mockMvc.perform(get("/departements/population/34/50000/100000"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].nom").value("Béziers"));
    }

    @Test
    void testGetVilleEntreAucuneVille() throws Exception {
        when(villeService.extractVilles("34", 1, 10)).thenThrow(new ExceptionFonctionnelle("Aucune ville"));

        mockMvc.perform(get("/departements/population/34/1/10"))
            .andExpect(status().isBadRequest());
    }
}
