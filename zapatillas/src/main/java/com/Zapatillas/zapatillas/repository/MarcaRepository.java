package com.Zapatillas.zapatillas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Zapatillas.zapatillas.model.Marca;

@Repository
public interface MarcaRepository extends JpaRepository<Marca,Integer>{

}
