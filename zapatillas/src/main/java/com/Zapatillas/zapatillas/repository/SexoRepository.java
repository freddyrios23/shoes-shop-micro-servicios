package com.Zapatillas.zapatillas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Zapatillas.zapatillas.model.Sexo;

@Repository
public interface SexoRepository extends JpaRepository<Sexo,Integer>{

}
