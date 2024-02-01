package com.example.azienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.azienda.models.Progetto;

@Repository
public interface ProgettoRepository extends JpaRepository<Progetto, Long>{

}
