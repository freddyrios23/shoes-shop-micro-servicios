package com.Zapatillas.zapatillas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Zapatillas.zapatillas.model.Colores;

@Repository
public interface ColoresRepository extends JpaRepository<Colores,Integer>{
}
