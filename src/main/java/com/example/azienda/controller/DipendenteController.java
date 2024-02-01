package com.example.azienda.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.azienda.models.Dipendente;
import com.example.azienda.repository.DipendenteRepository;
import com.example.azienda.service.DipendenteService;
import com.example.azienda.session.UtentiAttivi;
import com.example.azienda.specification.DipendenteSpecification;

@CrossOrigin(origins = "http://localhost")
@RestController
@RequestMapping(value = "/dipendenti")
public class DipendenteController {
	DipendenteRepository dipendenteRepository;
	private DipendenteService dipendenteService;
	UtentiAttivi utentiAttivi;

	@Autowired
	public DipendenteController(DipendenteRepository dipendenteRepository, DipendenteService dipendenteService, UtentiAttivi utentiAttivi) {
		this.dipendenteRepository = dipendenteRepository;
		this.dipendenteService = dipendenteService;
		this.utentiAttivi = utentiAttivi;
	}
	
	//query
	@GetMapping("/tuttiDipendenti") //ti da tutti i dipendenti
    public List<Dipendente> getAllDipendenti() {
        return dipendenteRepository.findAll();
    }
	
	@GetMapping("/tuttiCompetence") //ti da tutti i competence
    public List<String> getAllCompetence() {
		List<String> competence = new ArrayList<>();
		List<Dipendente> dip = dipendenteRepository.findAll();
		//evito le ripetizioni
		for (Dipendente dipendente : dip) 
		{
			String comp = dipendente.getCompetence();
		    //se la stringa comp non si ripete in dip, allora aggiungi a competence la stringa comp
			if (!competence.contains(comp)) 
			{
	            competence.add(comp);
	        }
		}
		return competence;
    }
	
	@GetMapping("/{id}") //cerca un dipendente dall'id
    public Dipendente getDipendenteById(@PathVariable Long id) {
        return dipendenteRepository.findById(id).orElse(null);
    }
	
	@GetMapping("/search/{s}") //cerca un dipentente dal nome
    public List<Dipendente> search(@PathVariable String s) {
        return  dipendenteRepository.findByNome(s);
    }
	
	@GetMapping("/search/nome/{s}") //cerca dipendente dal nome avente in mezzo una certa lettera/parola
    public List<Dipendente> searchNome(@PathVariable String s) {
		Specification<Dipendente> specification = DipendenteSpecification.hasFirstNameLike(s);
		List<Dipendente> dipendentiLike = dipendenteRepository.findAll(specification);
        return  dipendentiLike;
    }
	
	@PostMapping //crea una tabella dipendente in post
    public Dipendente createDipendente(@RequestBody Dipendente dip) {
        return dipendenteRepository.save(dip);
    }
	
	@GetMapping("/updateCellulare/{id}/{numero_cellulare}") //modifica il cellulare del dipendente con id specificato
    public Dipendente updateNumeroCellulare(@PathVariable Long id, @PathVariable String numero_cellulare) {
		Dipendente dip = dipendenteRepository.findById(id).orElse(null);
		dip.setNumero_cellulare(numero_cellulare);
        return dipendenteRepository.save(dip);
    }
	
	@GetMapping("/searchByCompetence/{id}") //dato un dip, cerca i dip con lo stesso compentenceCenter
    public List<Dipendente> searchByCompetenceCenter(@PathVariable Long id) {
		Dipendente dip = dipendenteRepository.findById(id).orElse(null);
		String competence_center = dip.getCompetence();
        return  dipendenteRepository.findByCompetence(competence_center);
    }
	
	@GetMapping("/mostraStipendioAlto") //mostra il dipendente con lo stipendio piu alto in ogni competence service
    public ResponseEntity<List<Dipendente>> mostraStipendioAlto() {
        List<Dipendente> risultato = dipendenteService.mostraStipendioAlto();
        return new ResponseEntity<>(risultato, HttpStatus.OK);
    }
	
	@PostMapping("/autenticazione")
	public String autenticazione(@RequestParam String email, @RequestParam String password) throws IOException {
	    // Logga i valori dei parametri per verificare se sono stati ricevuti correttamente
	    System.out.println("Email: " + email);
	    System.out.println("Password: " + password);
	    // Recupera l'oggetto Dipendente direttamente usando l'email (query che ti trova il dip dalla email)
	    Optional<Dipendente> dip = dipendenteRepository.findByEmail(email);
	    //se si è recuperato un oggetto 
	    if (dip.isPresent()) 
	    {
	    	//recupera l'oggetto che è risultato dalla query
	        Dipendente dipendente = dip.get();
	        String passwordDip = dipendente.getPassword();
	        String mansioneDip = dipendente.getMansione();
	        System.out.println("Mansione: " + mansioneDip);
	        
	        //se la password corrisponde e si tratta di uno sviluppatore, restituisci dipendente o hr
	        if (passwordDip.equals(password) && (mansioneDip.equals("sviluppatore front-end") || mansioneDip.equals("sviluppatore back-end"))) 
	        {
	        	Boolean flag = false; //si da per scontato che l'utente non ci sia e deve essere aggiunto
	        	for (Dipendente d : utentiAttivi.utenti) 
	        	{
	        		if(d.getEmail().equals(dipendente.getEmail())) 
	        		{
	        			//l'utente sta già negli utenti loggati e non deve essere aggiunto negli utenti attivi perchè c'è gia
	        			flag = true;
	        		}
	        	}
	        	//if per evitare la duplicazione nella lista di dipendenti loggati
	        	if(flag == false) 
	        	{
	        		//si aggiunge il dipendente tra gli utenti attivi
	        		utentiAttivi.addUtenti(dipendente);
	        	}
	            return "sviluppatore";
	        } 
	        else if (passwordDip.equals(password) && mansioneDip.equals("HR"))
	        {
	        	Boolean flag = false;
	        	for (Dipendente d : utentiAttivi.utenti) {
	        		if(d.getEmail().equals(dipendente.getEmail())) {
	        			flag = true;
	        		}
	        	}
	        	//if per evitare la duplicazione nella lista di dipendenti loggati
	        	if(flag == false) 
	        	{
		        	utentiAttivi.addUtenti(dipendente);
	        	}
	            return "HR";
	        }
	        else
	        {
	        	return "error";
	        }
	    } 
	    else 
	    {
	        // Se l'email non corrisponde a nessun dipendente, restituisci error
	        return "error";
	    }
	}
	
	@GetMapping("/utentiLoggati")
    public List<Dipendente> getUtentiLoggati() {
       return utentiAttivi.getUtenti();
    }
	
}
