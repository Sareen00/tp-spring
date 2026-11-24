package fr.diginamic.hello_tp1.services;


import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

import fr.diginamic.hello_tp1.entities.Departement;
import fr.diginamic.hello_tp1.exceptions.ExceptionFonctionnelle;
import fr.diginamic.hello_tp1.repositories.DepartementRepository;
import fr.diginamic.hello_tp1.services.interfaces.IDepartementService;

/**
 * Service contenant les règles métier sur les départements.
 */
@Service
public class DepartementService implements IDepartementService {

    // @Autowired
    // private DepartementDao departementDao;
    private final DepartementRepository departementRepository;


    DepartementService(DepartementRepository departementRepository) {
        this.departementRepository = departementRepository;
    }


    @Override
    public Iterable<Departement> extractAll(){
        return departementRepository.findAll();
    }


    @Override
    public Departement extractById(Integer id)throws ExceptionFonctionnelle{
        Departement departement = departementRepository.findById(id).orElseThrow(() -> new ExceptionFonctionnelle("Aucune ville avec l'id " + id));
        return departement;
    }


    @Override
    public Departement extractByCode(String code)throws ExceptionFonctionnelle{
        Departement Departement = departementRepository.findByCode(code);
        if (Departement == null) throw new ExceptionFonctionnelle("Aucun Departement avec ce code " + code);
        return Departement;
    }


    @Override
    public Departement extractByIdOrCode(Integer id, String code) {
        Departement dep = null;
        if (id != null) dep = departementRepository.findById(id).orElse(null);
        if (dep == null && code != null) dep = departementRepository.findByCode(code);
        return dep;
    }


    @Override
    @Transactional
    public void insertDepartement(Departement departement) throws ExceptionFonctionnelle {
        if (departementRepository.findByCode(departement.getCode()) != null) {
            throw new ExceptionFonctionnelle("Un département avec le code " + departement.getCode() + " existe déjà");
        }
        departementRepository.save(departement);
    }


    @Override
    @Transactional
    public void updateDepartement(Integer id, Departement newDepartement) throws ExceptionFonctionnelle {
        Departement departement = departementRepository.findById(id).orElse(null);
        if (departement == null) throw new ExceptionFonctionnelle("Aucune Departement avec l'id " + id);
        departement.setCode(newDepartement.getCode());
        departement.setNom(newDepartement.getNom());
        departement.setVilles(newDepartement.getVilles());
        departementRepository.save(newDepartement);
    }


    @Override
    @Transactional 
    public void deleteDepartement(Integer id) throws ExceptionFonctionnelle {
        Departement Departement = departementRepository.findById(id).orElseThrow(() -> new ExceptionFonctionnelle("Aucun département avec l'id " + id));
        departementRepository.delete(Departement);
    }


    @Override
    @Transactional
    public Departement creerDepartement(String code) {
        Departement dep = new Departement();
        dep.setCode(code);
        departementRepository.save(dep);
        return dep;
    }

    public void genererPdf(String code,Document document) throws DocumentException, IOException, ExceptionFonctionnelle{
        Departement departement = extractByCode(code);
        document.open();
        document.addTitle(departement.getNom());
    }
}
