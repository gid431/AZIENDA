package com.example.azienda.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.azienda.session.UtentiAttivi;

@Configuration
public class AppConfig {

	@Bean
    public UtentiAttivi activeUserStore() {
        return new UtentiAttivi();
    }
}
