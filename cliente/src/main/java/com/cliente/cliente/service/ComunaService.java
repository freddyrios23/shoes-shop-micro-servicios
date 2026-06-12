package com.cliente.cliente.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cliente.cliente.DTO.ComunaDTO;
import com.cliente.cliente.model.Comuna;
import com.cliente.cliente.repository.ComunaRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ComunaService {

    @Autowired
    private ComunaRepository comunaRepository;

    private ComunaDTO convertirDTO(Comuna comuna){
        ComunaDTO comunaDTO = new ComunaDTO();
        comunaDTO.setId(comuna.getId());
        comunaDTO.setNombre(comuna.getNombre());
        
        return comunaDTO;
    }

    public List<ComunaDTO> obtenerTodas(){
        log.info("Obteniendo todas las comunas");
        return comunaRepository.findAll().stream().map(this::convertirDTO).toList();
    }

    public ComunaDTO buscarPorID(Integer id){
        log.info("Buscando comuna con id {}", id);
        Comuna comuna = comunaRepository.findById(id)
        .orElseThrow(()-> {
            log.warn("No existe comuna con id {}", id);
            return new RuntimeException("¡La comuna no encontrada!");
        });
        log.info("Comuna encontrada: {}", comuna.getNombre());
        return convertirDTO(comuna);
    }

    public Comuna guardarComuna(Comuna comuna){
        log.info("Guardando comuna con nombre {}", comuna.getNombre());
        return comunaRepository.save(comuna);
    }

    public Comuna actualizarComuna (Integer id, Comuna comuna){
        log.info("Actualizando comuna con id {}", id);
        Comuna comunaEditada = comunaRepository.findById(id)
        .orElseThrow(()-> {
            log.warn("No existe comuna con id {}", id);
            return new RuntimeException("¡La comuna no encontrada!");
        });

        if (comuna.getNombre()!=null) {
            comunaEditada.setNombre(comuna.getNombre());
        }
        log.info("Comuna con id {} actualizada correctamente", id);
        return comunaRepository.save(comunaEditada);
    }

    public String eliminarComuna(Integer id){
        log.info("Intentando eliminar comuna con id {}", id);
        try {
            Comuna comuna = comunaRepository.findById(id)
            .orElseThrow(()-> {
                log.warn("No existe comuna con id {}", id);
                return new RuntimeException("¡Imposible eliminar! Comuna con el id " + id + " no existe");
            });
            comunaRepository.delete(comuna);
            log.info("Comuna con id {} eliminada correctamente", id);
            return "La comuna '" + comuna.getNombre() + "' ha sido eliminada exitosamente";
        } catch (Exception e) {
            log.error("Error al eliminar comuna con id {}: {}", id, e.getMessage());
            return e.getMessage();
        }
    }

}