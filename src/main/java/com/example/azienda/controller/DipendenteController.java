package com.example.azienda.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.azienda.models.Dipendente;
import com.example.azienda.repository.DipendenteRepository;
import com.example.azienda.service.DipendenteService;
import com.example.azienda.session.UtentiAttivi;

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
	
	//query
	@GetMapping("/tuttiSviluppatori") //ti da tutti gli sviluppatori front e back end
    public List<Dipendente> getAllSviluppatori() {
		List<Dipendente> sviluppatoriFront = dipendenteRepository.findByMansione("sviluppatore front-end");
		List<Dipendente> sviluppatoriBack = dipendenteRepository.findByMansione("sviluppatore back-end");
		List<Dipendente> sviluppatori = new ArrayList<>();
		for (Dipendente sviluppatorifront : sviluppatoriFront)
		{
			sviluppatori.add(sviluppatorifront);
		}
		for (Dipendente sviluppatoriback : sviluppatoriBack)
		{
			sviluppatori.add(sviluppatoriback);
		}
		return sviluppatori;
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
		Specification<Dipendente> specification = DipendenteService.hasFirstNameLike(s);
		List<Dipendente> dipendentiLike = dipendenteRepository.findAll(specification);
        return  dipendentiLike;
    }
	
	@PostMapping //crea un dipendente in post
    public String createDipendente(@RequestBody Dipendente dip) {
		//controllo se l'email immessa nel dip del front end è già presente in un dipendete già salvato nel db, cosa che non deve essere possibile
		Optional<Dipendente> dipOptional = dipendenteRepository.findByEmail(dip.getEmail());
		if (dipOptional.isPresent()) 
		{
			//se il risultato della query ha dato un dipOptional popolato, significa che è stata inserita una mail uguale
			return "email uguale";
		}
		else
		{
			System.out.println("Nome: " + dip.getNome());
			System.out.println("Cognome: " + dip.getCognome());
			System.out.println("Competence: " + dip.getCompetence());
			System.out.println("Mansione: " + dip.getMansione());
			System.out.println("Stipendio: " + dip.getStipendio());
			System.out.println("Data nascita: " + dip.getData_nascita());
			System.out.println("Stato nascita: " + dip.getStato_nascita());
			System.out.println("Provincia nascita: " + dip.getProvincia_nascita());
			System.out.println("Comune nascita: " + dip.getComune_nascita());
			System.out.println("Codice fiscale: " + dip.getCodice_fiscale());
			System.out.println("Numero cellulare: " + dip.getNumero_cellulare());
			System.out.println("Email: " + dip.getEmail());
			System.out.println("Password: " + dip.getPassword());
	        dipendenteRepository.save(dip);
	        return "inserimento ok";
		}
    }
	
	@GetMapping("/updateCellulare/{email}/{numero_cellulare}") //modifica il cellulare del dipendente con email specificata
    public Dipendente updateNumeroCellulare(@PathVariable String email, @PathVariable String numero_cellulare) {
		Optional<Dipendente> dipOptional = dipendenteRepository.findByEmail(email);
		Dipendente dipendente = dipOptional.get();
		dipendente.setNumero_cellulare(numero_cellulare);
        return dipendenteRepository.save(dipendente);
    }
	
	@GetMapping("/searchByCompetence/{email}") //dato un dipendente, cerca i dipendenti con lo stesso compentenceCenter
    public List<Dipendente> searchByCompetenceCenter(@PathVariable String email) {
		Dipendente dip = dipendenteRepository.findByEmail(email).orElse(null);
		String competence_center = dip.getCompetence();
        return  dipendenteRepository.findByCompetence(competence_center);
    }
	
	@GetMapping("/getMansione/{email}") //dato una email di dipendente, ti da la sua mansione
    public String getMansione(@PathVariable String email) {
		Optional<Dipendente> dipOptional = dipendenteRepository.findByEmail(email);
		if(dipOptional.isPresent())
		{
			Dipendente dip = dipOptional.get();
			return dip.getMansione();
		}
		else
		{
			return "dipendente non esistente";
		}
    }
	
	@GetMapping("/mostraStipendiAlti") //mostra il dipendente con lo stipendio piu alto in ogni competence service
    public ResponseEntity<List<Dipendente>> mostraStipendiAlti() {
        List<Dipendente> risultato = dipendenteService.mostraStipendioAlto();
        return new ResponseEntity<>(risultato, HttpStatus.OK);
    }
	
	@GetMapping("/mostraStipendioAltoDipendente/{competence}") //dato un competence, mostra il dipendente con lo stipendio del competence service
    public ResponseEntity<List<Dipendente>> mostraStipendioAltoDipendente(@PathVariable String competence) {
        List<Dipendente> dipendenti = dipendenteRepository.findByCompetence(competence);
        //nuova lista dipendenti popolata dai dipendenti con lo stipendio piu alto che sarà creata nel foreach
        List<Dipendente> risultato = new ArrayList<>();
        double maxstip = 0;
        for(Dipendente dipendente : dipendenti) 
        {
        	if(dipendente.getStipendio() >= maxstip) 
        	{
        		maxstip = dipendente.getStipendio();
        	}
        }
        //per ogni elementi in dipendenti, dammi il valore dello stipendio
        //se è piu grande del precedente, mettilo nella lista di tipo dipendente risultato
        for(Dipendente dipendente : dipendenti)
        {
        	if(dipendente.getStipendio() >= maxstip)
        	{
        		risultato.add(dipendente);
        	}
        }
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
	        	Boolean flag = false; //si da per scontato che l'utente non ci sia negli utenti attivi e deve essere aggiunto
	        	for (Dipendente d : utentiAttivi.utenti) //controllo se l'utente è negli utenti attivi(loggati)
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
	        	//stesso controllo di prima sugli utenti loggati
	        	for (Dipendente d : utentiAttivi.utenti) 
	        	{
	        		if(d.getEmail().equals(dipendente.getEmail())) 
	        		{
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
	
	@GetMapping("/utentiLoggati") //mostra gli utenti loggati nella lista utenti attivi
    public List<Dipendente> getUtentiLoggati() {
       return utentiAttivi.getUtenti();
    }
	
	@GetMapping("/logout/{email}") //data la email di un utente, fa effettuare il logout
    public String logout (@PathVariable String email) {
		Optional<Dipendente> dipOptional = dipendenteRepository.findByEmail(email);
		if(dipOptional.isPresent()) 
		{
			Dipendente dipendente = dipOptional.get();
			Boolean ispresent = false; //si da per scontato che l'utente non ci sia negli utenti attivi e non deve essere eliminato
        	for (Dipendente d : utentiAttivi.utenti) //controllo se l'utente è negli utenti attivi(loggati)
        	{
        		if(d.getEmail().equals(dipendente.getEmail())) 
        		{
        			//l'utente sta negli utenti loggati e deve essere eliminato negli utenti attivi 
        			ispresent = true;
        			utentiAttivi.removeUtente(d);
        			if(ispresent)	break;
        		}
        	}
        	//if per evitare la duplicazione nella lista di dipendenti loggati
        	if(ispresent) 
        	{
        		//si aggiunge il dipendente tra gli utenti attivi
        		return "utente eliminato";
        	}
        	else
        	{
        		return "utente non loggato";
        	}	
		}
		else
		{
			return "utente non esistente";
		}	
    }
	
	@GetMapping("/getCellulareDipentente/{email}") //Data la mail di un dipendente, ti da il suo numero cellulare
    public String getCellulareDipentente(@PathVariable String email) {
		Dipendente dip = dipendenteRepository.findByEmail(email).orElse(null);
		String numero = dip.getNumero_cellulare();
		return numero;
    }
	
}
