package br.edu.infnet.sgp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Project Management API", version = "1.0", description = "API para gerenciamento de projetos"))
public class SgpApplication {

    public static void main(String[] args) {
        SpringApplication.run(SgpApplication.class, args);
    }

}
