package fr.diginamic.hello_tp1.exceptions;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Gestionnaire global des exceptions de l'API.
 */
@RestControllerAdvice 
public class GestionnaireException {
    
    /**
     * Traite les exceptions fonctionnelles.
     *
     * @param e exception levée
     * @return message d'erreur avec le statut 400
     */
    @ExceptionHandler({ExceptionFonctionnelle.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> traiterException(ExceptionFonctionnelle e){
        return ResponseEntity.badRequest().body(e.getMessage());  
    }

    /**
     * Traite les erreurs de validation des données reçues.
     *
     * @param e exception levée par la validation
     * @return liste des erreurs, une par ligne
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String traiterValidation(MethodArgumentNotValidException e) {
        return e.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + " : " + err.getDefaultMessage())
                .collect(Collectors.joining("\n"));
    }
}
