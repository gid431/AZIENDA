package com.example.azienda.specification;

import org.springframework.data.jpa.domain.Specification;

import com.example.azienda.models.*;

import jakarta.persistence.criteria.Join;

public class AssegnazioniSpecification {
	
	//fa il join tra assegnazioni e progetto con la clausula where avente idprogetto un valore
	public static Specification<Assegnazioni> hasAssegnazioneSuProgettoById(Long idProgetto) {
        return (root, query, criteriaBuilder) -> {
            Join<Assegnazioni,Progetto> x = root.join("progetto");
            return criteriaBuilder.equal(x.get("codice_progetto"), idProgetto); //where
        };
	}
	
	//cerca i dipendenti che stanno in un determinato progetto
	public static Specification<Assegnazioni> hasAssegnazioneSuProgettoByNomeProgetto(String nomeProgetto) {
        return (root, query, criteriaBuilder) -> {
            Join<Assegnazioni,Progetto> x = root.join("progetto");
            return criteriaBuilder.equal(x.get("nome"), nomeProgetto); //where
        };
	}
	
	//cerca i progetti che ha un determinato dipendente
	public static Specification<Assegnazioni> hasAssegnazioneSuProgettoByDipendente(String nomeDip) {
        return (root, query, criteriaBuilder) -> {
            Join<Assegnazioni,Dipendente> x = root.join("dipendente");
            return criteriaBuilder.equal(x.get("nome"), nomeDip); //where
        };
	}
	
	//fa il join tra assegnazioni e progetto
    public static Specification<Assegnazioni> hasAssegnazioneSuProgetto() { 
        return (root, query, criteriaBuilder) -> {
            Join<Assegnazioni,Progetto> x = root.join("progetto"); //join
            return criteriaBuilder.isNotNull(x); //where vuoto
        };
    }
    
    //fa il join tra assegnazioni e dipendente
    public static Specification<Assegnazioni> hasAssegnazioneSuDipendente() { 
        return (root, query, criteriaBuilder) -> {
            Join<Assegnazioni,Dipendente> x = root.join("dipendente");
            return criteriaBuilder.isNotNull(x);
        };
    }
}