package com.example.azienda.specification;

import org.springframework.data.jpa.domain.Specification;

import com.example.azienda.models.Dipendente;

public class DipendenteSpecification {
	
	public static Specification<Dipendente> hasFirstNameLike(String name) {
        return (root, query, criteriaBuilder) ->
          criteriaBuilder.like(root.<String>get("nome"), "%" + name + "%");
    }

    public static Specification<Dipendente> hasLastName(String name) {
        return (root, query, cb) ->
          cb.equal(root.<String>get("cognome"), name);
    }
    
}
