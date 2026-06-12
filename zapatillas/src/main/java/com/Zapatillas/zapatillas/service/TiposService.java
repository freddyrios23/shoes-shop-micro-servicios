package com.Zapatillas.zapatillas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Zapatillas.zapatillas.model.Tipos;
import com.Zapatillas.zapatillas.repository.TiposRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class TiposService {
    @Autowired
    private TiposRepository tiposRepository;

    public List<Tipos> obtenerTodas(){
        log.info("Obteniendo todas las relaciones entre tipos y zapatillas");
        return tiposRepository.findAll();
    }

    public String guardarRelacion(Tipos relacion){
        log.info("Guardando relación entre zapatilla {} y tipo {}", relacion.getZapatilla().getNombre(), relacion.getTipo().getNombre());
        tiposRepository.save(relacion);
        log.info("Relación guardada correctamente entre la zapatilla {} y el tipo {}", relacion.getZapatilla().getNombre(), relacion.getTipo().getNombre());
        return "El tipo de zapatilla" + relacion.getTipo().getId() + "El tipo de zapatilla fue agregado" + relacion.getTipo().getId();
    }
}