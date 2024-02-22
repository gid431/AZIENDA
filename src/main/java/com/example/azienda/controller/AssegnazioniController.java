package com.example.azienda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.azienda.models.Assegnazioni;
import com.example.azienda.models.Dipendente;
import com.example.azienda.models.Progetto;
import com.example.azienda.repository.AssegnazioniRepository;
import com.example.azienda.repository.DipendenteRepository;
import com.example.azienda.repository.ProgettoRepository;
import com.example.azienda.service.AssegnazioniService;

@CrossOrigin(origins = "http://localhost") //da quale origine arrivano le richieste
@RestController
@RequestMapping("/assegnazioni")
public class AssegnazioniController {
	AssegnazioniRepository assegnazioniRepository;
	DipendenteRepository dipendenteRepository;
	ProgettoRepository progettoRepository;

	@Autowired
	public AssegnazioniController(AssegnazioniRepository assegnazioniRepository, DipendenteRepository dipendenteRepository, ProgettoRepository progettoRepository) {
		this.assegnazioniRepository = assegnazioniRepository;
		this.dipendenteRepository = dipendenteRepository;
		this.progettoRepository = progettoRepository;
	}
	
	//query
	@GetMapping //ti da tutte le assegnazioni (ti fa già il join della tabelle tra progetto e dipendente)
    public List<Assegnazioni> getAllProgetti() {
        return assegnazioniRepository.findAll();
    }
	
	@GetMapping("/joinAssegnazioniProgetto/{idProgetto}") //fa vedere tutti i dipendenti su un determinato progetto con idProgetto = idProgetto
    public List<Assegnazioni> searchNome(@PathVariable Long idProgetto) {
		 Specification<Assegnazioni> specification = AssegnazioniService.hasAssegnazioneSuProgettoById(idProgetto);
		 List<Assegnazioni> ass = assegnazioniRepository.findAll(specification);
		 return ass;
    }
	
	@GetMapping("/joinAssegnazioniProgetto") 
    public List<Assegnazioni> joinAssegnazioniProgetto() {
		 Specification<Assegnazioni> specification = AssegnazioniService.hasAssegnazioneSuProgetto();
		 List<Assegnazioni> ass = assegnazioniRepository.findAll(specification);
		 return ass;
    }
	
	@GetMapping("/joinAssegnazioniDipendente") 
    public List<Assegnazioni> joinAssegnazioniDipendente() {
		 Specification<Assegnazioni> specification = AssegnazioniService.hasAssegnazioneSuDipendente();
		 List<Assegnazioni> ass = assegnazioniRepository.findAll(specification);
		 return ass;
    }
	
	@GetMapping("/dipendentiSuProgetto/{nomeProgetto}") //dato un nome di un progetto, mostra i dipendenti nel progetto (usata)
    public List<Assegnazioni> dipendentiSuProgetto(@PathVariable String nomeProgetto) {
		 Specification<Assegnazioni> specification = AssegnazioniService.hasAssegnazioneSuProgettoByNomeProgetto(nomeProgetto);
		 List<Assegnazioni> ass = assegnazioniRepository.findAll(specification);
		 return ass;
    }
	
	@GetMapping("/progettoDiDipendente/{email}") //data la mail di un dipendente, mostra i progetti a cui è assegnato
    public List<Assegnazioni> progettoDiDipdendente(@PathVariable String email) {
		Dipendente dip = dipendenteRepository.findByEmail(email).orElse(null);
		String nomeDip = dip.getNome();
		Specification<Assegnazioni> specification = AssegnazioniService.hasAssegnazioneSuProgettoByDipendente(nomeDip);
		List<Assegnazioni> ass = assegnazioniRepository.findAll(specification);
		return ass;
    }
	
	@GetMapping("/addAssegnazione/{email}/{nomeProgetto}") //data la mail di un dipendente, mostra i progetti a cui è assegnato
    public String addAssegnazione (@PathVariable String email, @PathVariable String nomeProgetto) {
		Dipendente dip = dipendenteRepository.findByEmail(email).orElse(null);
		Progetto prog = progettoRepository.findByNome(nomeProgetto).orElse(null);
		Assegnazioni ass = new Assegnazioni(); // Creazione di un nuovo oggetto Assegnazioni
		ass.setDipendente(dip); // Imposta il dipendente nell'oggetto Assegnazioni
		ass.setProgetto(prog); // Imposta il progetto nell'oggetto Assegnazioni
	    // Tentativo di salvare l'oggetto Assegnazioni nel repository
	    Assegnazioni savedAss = assegnazioniRepository.save(ass);
	    // Se il salvataggio ha avuto successo
	    if (savedAss != null) 
	    {
	        return "Assegnazioni salvata correttamente";
	    }
	    else
	    {
	    	return "Si è verificato un errore durante il salvataggio di Assegnazioni";	
	    }
    }
	
}
