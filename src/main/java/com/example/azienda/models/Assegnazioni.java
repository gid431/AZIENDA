package com.example.azienda.models;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.criteria.Join;

@Entity
public class Assegnazioni {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_sviluppatore")
    private Dipendente dipendente;

    @ManyToOne
    @JoinColumn(name = "id_progetto")
    private Progetto progetto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Dipendente getDipendente() {
		return dipendente;
	}

	public void setDipendente(Dipendente dipendente) {
		this.dipendente = dipendente;
	}

	public Progetto getProgetto() {
		return progetto;
	}

	public void setProgetto(Progetto progetto) {
		this.progetto = progetto;
	}
    
	public static Specification<Dipendente> hasSviluppatore(Long codice_dipendente) {
	    return (root, query, criteriaBuilder) -> {
	        Join<Object, Object> assegnazioniSviluppatori = root.join("sviluppatori");
	        return criteriaBuilder.equal(assegnazioniSviluppatori.get("nome"), codice_dipendente);
	    };
	}
    
}
