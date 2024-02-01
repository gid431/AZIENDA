package com.example.azienda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.azienda.models.Progetto;
import com.example.azienda.repository.ProgettoRepository;

@CrossOrigin(origins = "http://localhost") //da quale origine arrivano le richieste
@RestController
@RequestMapping("/progetti")
public class ProgettoController {
	ProgettoRepository progettoRepository;

	@Autowired
	public ProgettoController(ProgettoRepository progettoRepository) {
		this.progettoRepository = progettoRepository;
	}
	
	//query
	@GetMapping("/tuttiProgetti") //ti da tutti i progetti
    public List<Progetto> getAllProgetti() {
        return progettoRepository.findAll();
    }
	
	@PostMapping //crea una tabella progetto in post
    public Progetto createProgetto(@RequestBody Progetto dip) {
        return progettoRepository.save(dip);
    }
	
	
	
	
}
