package com.boletas20.boletas20.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boletas20.boletas20.Model.Boleta;

public interface BoletaRepository extends JpaRepository<Boleta,Integer> {

    Optional<Boleta> findFirstByBoletas_Id(Integer zapatillaId);
}
