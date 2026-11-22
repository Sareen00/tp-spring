package fr.diginamic.hello_tp1.exceptions;

/**
 * Exception levée lorsqu'une règle métier n'est pas respectée.
 */
public class ExceptionFonctionnelle extends Exception{
    /**
     * Construit l'exception avec un message.
     *
     * @param message message d'erreur renvoyé au client
     */
    public ExceptionFonctionnelle(String message) {
        super(message);
    }
}
