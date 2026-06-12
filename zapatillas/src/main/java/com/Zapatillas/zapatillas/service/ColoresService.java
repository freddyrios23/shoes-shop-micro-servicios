package com.Zapatillas.zapatillas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Zapatillas.zapatillas.model.Colores;
import com.Zapatillas.zapatillas.repository.ColoresRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;


@Service
@Transactional
@Slf4j
public class ColoresService {

    @Autowired
    private ColoresRepository coloresRepository;

    public List<Colores> obtenerTodos() {
        log.info("Obteniendo todas las relaciones entre colores y zapatillas");
        return coloresRepository.findAll();
    }

    public String guardarRelacion(Colores relacion) {
        log.info("Guardando relación entre zapatilla {} y color {}", relacion.getZapatilla().getNombre(), relacion.getColor().getNombre());
        coloresRepository.save(relacion);
        log.info("Relación guardada exitosamente entre la zapatilla {} y el color {}", relacion.getZapatilla().getNombre(), relacion.getColor().getNombre());
        return "La zapatilla " + relacion.getZapatilla().getNombre()
                + " fue asignada al color " + relacion.getColor().getNombre();               
    }
}