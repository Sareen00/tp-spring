package fr.diginamic.hello_tp1.services;

import org.springframework.stereotype.Service;

@Service
public class HelloService {
    public String salutation(){
        return "Je suis la classe service et je vous dis Bonjour";
    }
}
