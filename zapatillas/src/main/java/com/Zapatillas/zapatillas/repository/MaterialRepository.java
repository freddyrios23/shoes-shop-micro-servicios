package com.Zapatillas.zapatillas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Zapatillas.zapatillas.model.Material;

@Repository
public  interface MaterialRepository  extends JpaRepository<Material,Integer> {
}
