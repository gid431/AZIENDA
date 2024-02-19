package com.example.azienda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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
	
	//ti cerca un dipendente che ha un determinato nome tra stringhe indicate dalle %
	public static Specification<Dipendente> hasFirstNameLike(String name) {
        return (root, query, criteriaBuilder) ->
          criteriaBuilder.like(root.<String>get("nome"), "%" + name + "%");
    }

	//mostra un dipendente che ha un determinato cognome
    public static Specification<Dipendente> hasLastName(String cognome) {
        return (root, query, cb) ->
          cb.equal(root.<String>get("cognome"), cognome);
    }
}
