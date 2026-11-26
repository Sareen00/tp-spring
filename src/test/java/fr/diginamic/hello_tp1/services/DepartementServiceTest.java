package fr.diginamic.hello_tp1.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import fr.diginamic.hello_tp1.entities.Departement;
import fr.diginamic.hello_tp1.exceptions.ExceptionFonctionnelle;
import fr.diginamic.hello_tp1.repositories.DepartementRepository;

/**
 * Tests unitaires de DepartementService avec un mock de DepartementRepository.
 */
public class DepartementServiceTest {

    @Mock
    private DepartementRepository departementRepository;

    @InjectMocks
    private DepartementService departementService;

    private Departement herault;

    @BeforeEach
    public void before() {
        MockitoAnnotations.openMocks(this);

        herault = new Departement();
        herault.setId(1);
        herault.setCode("34");
        herault.setNom("Hérault");
    }


    @Test
    void testExtractAll() {
        Departement gard = new Departement();
        gard.setCode("30");
        gard.setNom("Gard");
        when(departementRepository.findAll()).thenReturn(List.of(herault, gard));

        Iterable<Departement> resultat = departementService.extractAll();

        assertThat(resultat).hasSize(2).extracting(Departement::getNom).contains("Hérault", "Gard");
    }

    @Test
    void testExtractAllVide() {
        when(departementRepository.findAll()).thenReturn(new ArrayList<>());

        assertThat(departementService.extractAll()).isEmpty();
    }

    @Test
    void testExtractById() throws ExceptionFonctionnelle {
        when(departementRepository.findById(1)).thenReturn(Optional.of(herault));

        Departement resultat = departementService.extractById(1);

        assertEquals("Hérault", resultat.getNom());
    }

    @Test
    void testExtractByIdInconnu() {
        when(departementRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ExceptionFonctionnelle.class, () -> departementService.extractById(99));
    }


    @Test
    void testExtractByCode() throws ExceptionFonctionnelle {
        when(departementRepository.findByCode("34")).thenReturn(herault);

        Departement resultat = departementService.extractByCode("34");

        assertEquals("Hérault", resultat.getNom());
    }

    @Test
    void testExtractByCodeInconnu() {
        when(departementRepository.findByCode("00")).thenReturn(null);

        assertThrows(ExceptionFonctionnelle.class, () -> departementService.extractByCode("00"));
    }

    // ---------- extractByIdOrCode ----------

    @Test
    void testExtractByIdOrCodeParId() {
        when(departementRepository.findById(1)).thenReturn(Optional.of(herault));

        Departement resultat = departementService.extractByIdOrCode(1, null);

        assertEquals("34", resultat.getCode());
    }

    @Test
    void testExtractByIdOrCodeParCode() {
        when(departementRepository.findById(99)).thenReturn(Optional.empty());
        when(departementRepository.findByCode("34")).thenReturn(herault);

        Departement resultat = departementService.extractByIdOrCode(99, "34");

        assertEquals("Hérault", resultat.getNom());
    }

    @Test
    void testExtractByIdOrCodeIntrouvable() {
        when(departementRepository.findById(99)).thenReturn(Optional.empty());
        when(departementRepository.findByCode("00")).thenReturn(null);

        assertNull(departementService.extractByIdOrCode(99, "00"));
    }

    // ---------- insertDepartement ----------

    @Test
    void testInsertDepartement() throws ExceptionFonctionnelle {
        when(departementRepository.findByCode("34")).thenReturn(null);

        departementService.insertDepartement(herault);

        verify(departementRepository).save(herault);
    }

    @Test
    void testInsertDepartementDejaExistant() {
        when(departementRepository.findByCode("34")).thenReturn(herault);

        assertThrows(ExceptionFonctionnelle.class, () -> departementService.insertDepartement(herault));
        verify(departementRepository, never()).save(any());
    }

    @Test
    void testUpdateDepartement() throws ExceptionFonctionnelle {
        Departement modif = new Departement();
        modif.setCode("34");
        modif.setNom("Herault modifié");
        when(departementRepository.findById(1)).thenReturn(Optional.of(herault));

        departementService.updateDepartement(1, modif);

        assertEquals("Herault modifié", herault.getNom());
        verify(departementRepository).save(any(Departement.class));
    }

    @Test
    void testUpdateDepartementInconnu() {
        when(departementRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ExceptionFonctionnelle.class, () -> departementService.updateDepartement(99, herault));
        verify(departementRepository, never()).save(any());
    }

    // ---------- deleteDepartement ----------

    @Test
    void testDeleteDepartement() throws ExceptionFonctionnelle {
        when(departementRepository.findById(1)).thenReturn(Optional.of(herault));

        departementService.deleteDepartement(1);

        verify(departementRepository).delete(herault);
    }

    @Test
    void testDeleteDepartementInconnu() {
        when(departementRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ExceptionFonctionnelle.class, () -> departementService.deleteDepartement(99));
        verify(departementRepository, never()).delete(any());
    }

    // ---------- creerDepartement ----------

    @Test
    void testCreerDepartement() {
        Departement resultat = departementService.creerDepartement("13");

        assertEquals("13", resultat.getCode());
        verify(departementRepository).save(resultat);
    }

    @Test
    void testInitDataDesactive() throws ExceptionFonctionnelle {
        // @Value n'est pas rempli par Mockito, on met la valeur à la main
        ReflectionTestUtils.setField(departementService, "init", false);

        departementService.initData();

        verifyNoInteractions(departementRepository);
    }
}
