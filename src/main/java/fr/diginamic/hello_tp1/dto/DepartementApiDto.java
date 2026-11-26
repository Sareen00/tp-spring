package fr.diginamic.hello_tp1.dto;

/**
 * Département tel que renvoyé par l'API https://geo.api.gouv.fr/departements.
 */
public class DepartementApiDto {
    private String nom;
    private String code;
    private String codeRegion;
    
    /**
     * @return nom du département
     */
    public String getNom() {
        return nom;
    }
    /**
     * @param nom nom à définir
     */
    public void setNom(String nom) {
        this.nom = nom;
    }
    /**
     * @return code du département
     */
    public String getCode() {
        return code;
    }
    /**
     * @param code code à définir
     */
    public void setCode(String code) {
        this.code = code;
    }
    /**
     * @return code de la région du département
     */
    public String getCodeRegion() {
        return codeRegion;
    }
    /**
     * @param codeRegion code région à définir
     */
    public void setCodeRegion(String codeRegion) {
        this.codeRegion = codeRegion;
    }
    
}
