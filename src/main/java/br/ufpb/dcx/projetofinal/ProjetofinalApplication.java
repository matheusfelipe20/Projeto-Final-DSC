package br.ufpb.dcx.projetofinal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProjetofinalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjetofinalApplication.class, args);
    }

}
