package com.example.azienda.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.azienda.models.Dipendente;

@Repository
public interface DipendenteRepository extends JpaRepository<Dipendente, Long>, JpaSpecificationExecutor<Dipendente>{

	List<Dipendente> findByNome(String s);
	
	Optional<Dipendente> findByEmail(String s);
	
	List<Dipendente> findByCompetence(String s); //jpa che trova dal valore di competence, tipo un where
	
	//mostra il dipendente con lo stipendio piu alto in ogni competence service
	@Query("SELECT new Dipendente(d.nome, d.cognome, d.competence, max(d.stipendio)) FROM Dipendente AS d GROUP BY d.competence, d.nome, d.cognome")
	List<Dipendente> mostraStipendioAlto();

	
}