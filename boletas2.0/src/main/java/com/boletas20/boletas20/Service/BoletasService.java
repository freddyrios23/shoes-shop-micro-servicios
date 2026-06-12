package com.boletas20.boletas20.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boletas20.boletas20.Model.Boletas;
import com.boletas20.boletas20.Repository.BoletasRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class BoletasService {
@Autowired
    private BoletasRepository boletasRepository;

    public List<Boletas> obtenerTodas(){
        log.info("Obteniendo todas las relaciones entre boletas y zapatillas");
        return boletasRepository.findAll();
    }

    public String guardarRelacion(Boletas relacion){
        log.info("Guardando relación entre boleta y zapatilla");
        boletasRepository.save(relacion);
        log.info("Relación guardada exitosamente");
        return "La zapatilla" + relacion.getBoleta().getTotal() + "fue agregada a la boleta" + relacion.getBoleta().getId();
    }   
}
