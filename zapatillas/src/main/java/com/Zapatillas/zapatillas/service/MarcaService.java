package com.Zapatillas.zapatillas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Zapatillas.zapatillas.DTO.MarcaDTO;
import com.Zapatillas.zapatillas.model.Marca;
import com.Zapatillas.zapatillas.repository.MarcaRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class MarcaService {
    @Autowired
    private MarcaRepository marcaRepository;

    private MarcaDTO convertirDTO (Marca marca){
        MarcaDTO marcaDTO = new MarcaDTO();
        marcaDTO.setId(marca.getId());
        marcaDTO.setNombre(marca.getNombre());
        return marcaDTO;
    }

    public List<MarcaDTO> obtenerTodas(){
        log.info("Obteniendo todas las marcas");
        return marcaRepository.findAll().stream().map(this::convertirDTO).toList();
    }

    public MarcaDTO buscarPorId(Integer id){
        log.info("Buscando marca con id {}", id);
        Marca marca=marcaRepository.findById(id).orElseThrow(()-> {
            log.warn("No existe marca con id {}", id);
            return new RuntimeException("¡La marca no encontrada!");
        });
        log.info("Marca encontrada: {}", marca.getNombre());
        return convertirDTO(marca);
    }

    public Marca guardarMarca(Marca marca){
        log.info("Guardando marca con nombre {}", marca.getNombre());
        return marcaRepository.save(marca);
    }

    public Marca actualizarMarca (Integer id,Marca marca){
        log.info("Actualizando marca con id {}", id);
        Marca mark = marcaRepository.findById(id).orElseThrow(()-> {
            log.warn("No existe marca con id {}", id);
            return new RuntimeException("¡La marca no encontrada!");
        });
        if (marca.getNombre()!=null) {
            mark.setNombre(marca.getNombre());
        }
        log.info("Marca con id {} actualizada correctamente", id);
        return marcaRepository.save(mark);
    }

    public String eliminarMarca(Integer id){
        log.info("Intentando eliminar marca con id {}", id);
        try {
            Marca marca = marcaRepository.findById(id).orElseThrow(()-> {
                log.warn("No existe marca con id {}", id);
                return new RuntimeException("¡Imposible eliminar! Marca con el id " + id + " no existe");
            });
            marcaRepository.delete(marca);
            log.info("Marca con id {} eliminada correctamente", id);
            return "La Marca '" + marca.getId() + "' ha sido eliminada exitosamente";
        } catch (Exception e) {
            log.error("Error al eliminar marca con id {}: {}", id, e.getMessage());
            return e.getMessage();
        }
    }

}
