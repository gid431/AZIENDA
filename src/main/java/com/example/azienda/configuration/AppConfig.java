package com.example.azienda.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;


import com.example.azienda.session.UtentiAttivi;

@Configuration
public class AppConfig {

	@Bean
    public UtentiAttivi activeUserStore() {
        return new UtentiAttivi();
    }
}
