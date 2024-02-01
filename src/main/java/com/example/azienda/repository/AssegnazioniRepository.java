package com.example.azienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.azienda.models.Assegnazioni;

@Repository
public interface AssegnazioniRepository extends JpaRepository<Assegnazioni, Long>, JpaSpecificationExecutor<Assegnazioni>{

}
