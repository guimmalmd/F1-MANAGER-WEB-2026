package br.com.fiap.f1managerweb.tests;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //Avisa que essa classe é a principal da aplicação Spring Boot
public class F1ManagerWebApplication {
    public static void main(String[] args) {

        SpringApplication.run(F1ManagerWebApplication.class, args); //Inicia a aplicação e o servidor
    }

}
