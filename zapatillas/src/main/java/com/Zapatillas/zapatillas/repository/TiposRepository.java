package com.Zapatillas.zapatillas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Zapatillas.zapatillas.model.Tipos;

@Repository
public interface TiposRepository extends JpaRepository<Tipos,Integer> {

}