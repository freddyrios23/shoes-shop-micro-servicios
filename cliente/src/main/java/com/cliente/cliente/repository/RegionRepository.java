package com.cliente.cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cliente.cliente.model.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region,Integer>{

}
