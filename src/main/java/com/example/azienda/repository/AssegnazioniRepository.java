package com.example.azienda.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.azienda.models.Assegnazioni;

@Repository
public interface AssegnazioniRepository extends JpaRepository<Assegnazioni, Long>, JpaSpecificationExecutor<Assegnazioni>
{
	
	List<Assegnazioni> findByDipendenteIdDipendente(Long codice_dipendente);
}
