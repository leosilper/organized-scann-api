package br.com.fiap.organized_scann_api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@OpenAPIDefinition(info = @Info(title = "Organized Scann API", version = "1.0", description = "API para gestão de motos por portais de manutenção"))
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
