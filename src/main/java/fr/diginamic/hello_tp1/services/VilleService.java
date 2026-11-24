package fr.diginamic.hello_tp1.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.diginamic.hello_tp1.entities.Ville;
import fr.diginamic.hello_tp1.exceptions.ExceptionFonctionnelle;
import fr.diginamic.hello_tp1.repositories.VilleRepository;
import fr.diginamic.hello_tp1.services.interfaces.IVilleService;

/**
 * Service contenant les règles métier sur les villes.
 */
@Service 
public class VilleService implements IVilleService {

    // @Autowired
    // private VilleDao villeDao;

    private static final Logger LOG = LoggerFactory.getLogger(VilleService.class);
    private final VilleRepository villeRepository;


    VilleService(VilleRepository villeRepository) {
        this.villeRepository = villeRepository;
    }


    @Override
    public List<Ville> extractVilles(){
        return villeRepository.findAll();
    }


    @Override
    public List<Ville> extractVillesPaginate(int page, int size) {
        PageRequest pagination = PageRequest.of(page, size);
        return villeRepository.findAll(pagination).getContent();
    }


    @Override
    public Ville extractVille(Integer idVille)throws ExceptionFonctionnelle{
        LOG.debug("Recherche de la ville d'id {}", idVille);
        Ville ville = villeRepository.findById(idVille).orElseThrow(() -> new ExceptionFonctionnelle("Aucune ville avec l'id " + idVille));
        return ville;
    }


    @Override
    public List<Ville> extractVilles(String prefix)throws ExceptionFonctionnelle{
        if (villeRepository.findByNomStartingWith(prefix).isEmpty()) {
            throw new ExceptionFonctionnelle("Aucune ville ne commence par " + prefix);
        }
        return villeRepository.findByNomStartingWith(prefix);
    }


    @Override
    public List<Ville> extractVilles(int min, int max)throws ExceptionFonctionnelle{
        if (villeRepository.findByPopulationBetweenOrderByPopulationDesc(min, max).isEmpty()) {
            throw new ExceptionFonctionnelle("Aucune ville avec une population entre " + min + " et " + max);
        }
        return villeRepository.findByPopulationBetweenOrderByPopulationDesc(min, max);
    }

    @Override
    public List<Ville> extractVilles(int code, int min, int max)throws ExceptionFonctionnelle{
        if (villeRepository.findByDepartementCodeAndPopulationBetweenOrderByPopulationDesc(code, min, max).isEmpty()) {
            throw new ExceptionFonctionnelle("Aucune ville avec une population entre " + min + " et " + max + " dans le departement " + code);
        }
        return villeRepository.findByDepartementCodeAndPopulationBetweenOrderByPopulationDesc(code, min, max);
    }

    @Override
    public List<Ville> extractVillesDeDep(Integer code, int nbVille)throws ExceptionFonctionnelle{
        if (villeRepository.findByDepartementCodeOrderByPopulationDesc(code,Limit.of(nbVille)).isEmpty()) {
            throw new ExceptionFonctionnelle("Aucune ville dans le département " + code);
        }
        return villeRepository.findByDepartementCodeOrderByPopulationDesc(code,Limit.of(nbVille));
    }


    @Override
    @Transactional 
    public List<Ville> insertVille(Ville ville)throws ExceptionFonctionnelle{
        if (villeRepository.existsByNomIgnoreCase(ville.getNom())) {
                throw new ExceptionFonctionnelle("Une ville avec ce nom existe déjà");
        }
        villeRepository.save(ville);
        return villeRepository.findAll();
    }


    @Override
    @Transactional
    public List<Ville> updateVille(Integer id, Ville newVille) throws ExceptionFonctionnelle {
        Ville ville = villeRepository.findById(id).orElseThrow(() -> new ExceptionFonctionnelle("Aucune ville avec l'id " + id));
        ville.setNom(newVille.getNom());
        ville.setPopulation(newVille.getPopulation());
        ville.setDepartement(newVille.getDepartement());
        villeRepository.save(ville);
        return villeRepository.findAll();  
    }

    @Override
    @Transactional
    public List<Ville> deleteVille(Integer id) throws ExceptionFonctionnelle {
        Ville ville = villeRepository.findById(id).orElseThrow(() -> new ExceptionFonctionnelle("Aucune ville avec l'id " + id));
        villeRepository.delete(ville);
        return villeRepository.findAll();
    }
}