package com.example.azienda.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) 
    {
        registry.addMapping("/**") // Applica le configurazioni CORS a tutti gli endpoint
            .allowedOrigins("http://localhost:8080") // Abilita le richieste da questo dominio
            .allowedMethods("GET", "POST", "PUT", "DELETE"); // Specifica i metodi HTTP consentiti
    }
    
}
