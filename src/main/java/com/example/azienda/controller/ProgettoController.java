package com.example.azienda.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.azienda.models.Assegnazioni;
import com.example.azienda.models.Dipendente;
import com.example.azienda.models.Progetto;
import com.example.azienda.repository.DipendenteRepository;
import com.example.azienda.repository.ProgettoRepository;
import com.example.azienda.service.AssegnazioniService;

@CrossOrigin(origins = "http://localhost") //da quale origine arrivano le richieste
@RestController
@RequestMapping("/progetti")
public class ProgettoController {
	ProgettoRepository progettoRepository;
	DipendenteRepository dipendenteRepository;

	@Autowired
	public ProgettoController(ProgettoRepository progettoRepository, DipendenteRepository dipendenteRepository) {
		this.progettoRepository = progettoRepository;
		this.dipendenteRepository = dipendenteRepository;
	}
	
	//query
	@GetMapping("/tuttiProgetti") //ti da tutti i progetti
    public List<Progetto> getAllProgetti() {
        return progettoRepository.findAll();
    }
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE) //inserisce un progetto dato il nome di un progetto
    public String createProgetto(@RequestBody Progetto prog) {
		Optional<Progetto> progettoOptional = progettoRepository.findByNome(prog.getNome());
		if(progettoOptional.isPresent()) //il nome inserito in input dall'utente è già presente nel db 
		{
			return "errore";
		}
		else
		{
			progettoRepository.save(prog);
			return "ok";
		}
    }
	
	@GetMapping("/progettiNonAssegnatiADip/{email}") //ti da tutti i progetti non ancora assegnati ad un determinato dipendente, dato dalla email
    public List<Progetto> getProgettiNonAssegnatiADip(@PathVariable String email) {
		Dipendente dip = dipendenteRepository.findByEmail(email).orElse(null);
		Long idDip = dip.getidDipendente();
		List<Progetto> progetti = progettoRepository.getProgettiNonAssegnatiADipendente(idDip);
		return progetti;
    }
	
}
