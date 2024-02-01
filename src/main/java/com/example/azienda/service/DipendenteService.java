package com.example.azienda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.azienda.repository.DipendenteRepository;
import com.example.azienda.models.Dipendente;

@Service
public class DipendenteService {

	@Autowired
	private DipendenteRepository dipendenteRepository;
	
	//mostra il dipendente con lo stipendio piu alto in ogni competence service
	public List<Dipendente> mostraStipendioAlto() {
        return dipendenteRepository.mostraStipendioAlto();
    }
}
