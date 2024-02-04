package com.example.azienda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.azienda.models.Assegnazioni;
import com.example.azienda.models.Dipendente;
import com.example.azienda.repository.AssegnazioniRepository;
import com.example.azienda.repository.DipendenteRepository;
import com.example.azienda.specification.AssegnazioniSpecification;

@CrossOrigin(origins = "http://localhost") //da quale origine arrivano le richieste
@RestController
@RequestMapping("/assegnazioni")
public class AssegnazioniController {
	AssegnazioniRepository assegnazioniRepository;
	DipendenteRepository dipendenteRepository;

	@Autowired
	public AssegnazioniController(AssegnazioniRepository assegnazioniRepository, DipendenteRepository dipendenteRepository) {
		this.assegnazioniRepository = assegnazioniRepository;
		this.dipendenteRepository = dipendenteRepository;
	}
	
	//query
	@GetMapping //ti da tutte le assegnazioni (ti fa già il join della tabelle tra progetto e dipendente)
    public List<Assegnazioni> getAllProgetti() {
        return assegnazioniRepository.findAll();
    }
	
	@GetMapping("/joinAssegnazioniProgetto/{idProgetto}") //fa vedere tutti i dipendenti su un determinato progetto con idProgetto = idProgetto
    public List<Assegnazioni> searchNome(@PathVariable Long idProgetto) {
		 Specification<Assegnazioni> specification = AssegnazioniSpecification.hasAssegnazioneSuProgettoById(idProgetto);
		 List<Assegnazioni> ass = assegnazioniRepository.findAll(specification);
		 return ass;
    }
	
	@GetMapping("/joinAssegnazioniProgetto") 
    public List<Assegnazioni> joinAssegnazioniProgetto() {
		 Specification<Assegnazioni> specification = AssegnazioniSpecification.hasAssegnazioneSuProgetto();
		 List<Assegnazioni> ass = assegnazioniRepository.findAll(specification);
		 return ass;
    }
	
	@GetMapping("/joinAssegnazioniDipendente") 
    public List<Assegnazioni> joinAssegnazioniDipendente() {
		 Specification<Assegnazioni> specification = AssegnazioniSpecification.hasAssegnazioneSuDipendente();
		 List<Assegnazioni> ass = assegnazioniRepository.findAll(specification);
		 return ass;
    }
	
	@GetMapping("/dipendentiSuProgetto/{nomeProgetto}") //dato un nome di un progetto, mostra i dipendenti nel progetto (usata)
    public List<Assegnazioni> dipendentiSuProgetto(@PathVariable String nomeProgetto) {
		 Specification<Assegnazioni> specification = AssegnazioniSpecification.hasAssegnazioneSuProgettoByNomeProgetto(nomeProgetto);
		 List<Assegnazioni> ass = assegnazioniRepository.findAll(specification);
		 return ass;
    }
	
	@GetMapping("/progettoDiDipendente/{id}") //dato dip, mostra i suoi progetti
    public List<Assegnazioni> progettoDiDipdendente(@PathVariable Long id) {
		Dipendente dip = dipendenteRepository.findById(id).orElse(null);
		String nomeDip = dip.getNome();
		Specification<Assegnazioni> specification = AssegnazioniSpecification.hasAssegnazioneSuProgettoByDipendente(nomeDip);
		List<Assegnazioni> ass = assegnazioniRepository.findAll(specification);
		return ass;
    }
	
}
