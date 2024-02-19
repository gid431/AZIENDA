package com.example.azienda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.azienda.models.Progetto;

@Repository
public interface ProgettoRepository extends JpaRepository<Progetto, Long>{
	
	//ti mostra i progetti che non sono assegnati ad un dato dipendente preso con id
	@Query("SELECT p FROM Progetto p WHERE NOT EXISTS " +
	           "(SELECT a FROM Assegnazioni a WHERE a.progetto = p AND a.dipendente.idDipendente = :idDipendente)")
	List<Progetto> getProgettiNonAssegnatiADipendente(@Param("idDipendente") Long idDipendente);
}
