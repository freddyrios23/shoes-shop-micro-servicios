package com.Zapatillas.zapatillas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Zapatillas.zapatillas.model.Materiales;
import com.Zapatillas.zapatillas.repository.MaterialesRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class MaterialesService {
    @Autowired
    private MaterialesRepository materialesRepository;

    public List<Materiales> obtenerTodos() {
        log.info("Obteniendo todas las relaciones entre materiales y zapatillas");
        return materialesRepository.findAll();
    }

    public String guardarRelacion(Materiales relacion) {
        log.info("Guardando relación entre zapatilla {} y material {}", relacion.getZapatilla().getNombre(), relacion.getMaterial().getNombre());
        materialesRepository.save(relacion);
        log.info("Relación guardada correctamente");

        return "La zapatilla " + relacion.getZapatilla().getNombre()
                + " fue fabricada con el material " + relacion.getMaterial().getNombre();
    }

}
