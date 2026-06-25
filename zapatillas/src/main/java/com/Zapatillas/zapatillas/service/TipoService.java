package com.Zapatillas.zapatillas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Zapatillas.zapatillas.DTO.TipoDTO;
import com.Zapatillas.zapatillas.model.Tipo;
import com.Zapatillas.zapatillas.model.Zapatilla;
import com.Zapatillas.zapatillas.repository.TipoRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;


@Service
@Transactional
@Slf4j
public class TipoService {

    @Autowired
    private TipoRepository tipoRepository;

    public List<TipoDTO> obtenertodos(){
        log.info("Obteniendo todos los tipos");
        return tipoRepository.findAll().stream().map(this::convertirDto).toList();
    }

    public TipoDTO buscarPorId(Integer id){
        log.info("Buscando tipo con id {}", id);
        Tipo tipo = tipoRepository.findById(id)
        .orElseThrow(()-> {
            log.warn("No existe tipo con id {}", id);
            return new RuntimeException("¡Tipo no encontrado!");
        });
        log.info("Tipo encontrado: {}", tipo.getNombre());
        return convertirDto(tipo);
    }

    public TipoDTO guardaTipo(Tipo tipo){
        log.info("Guardando Tipo {}",tipo.getNombre());

        Tipo nuevoTipo = tipoRepository.save(tipo);

        log.info("Tipo guardado exitosamente con id {}",nuevoTipo.getId());

        return convertirDto(nuevoTipo);
    }

    public Tipo actualizarTipo(Integer id,Tipo tipo){
        log.info("Actualizando tipo con id {}", id);
        Tipo tipe = tipoRepository.findById(id)
        .orElseThrow(()-> {
            log.warn("No existe tipo con id {}", id);
            return new RuntimeException("¡Tipo no encontrado en los registros!");
        });
        if (tipo.getNombre()!= null) {
            tipe.setNombre(tipo.getNombre());
        }
        log.info("Tipo con id {} actualizado correctamente", id);
        return tipoRepository.save(tipe);
    }

    public String eliminarTipo(Integer id){
        log.info("Intentando eliminar tipo con id {}", id);
        try {
            Tipo tipo = tipoRepository.findById(id)
            .orElseThrow(()-> {
                log.warn("No existe tipo con id {}", id);
                return new RuntimeException("¡Imposible eliminar! El tipo con el id" + id + "no existe");
            });
            tipoRepository.delete(tipo);
            log.info("Tipo con id {} eliminado correctamente", id);
            return "El tipo '" + tipo.getNombre() + "' ha sido eliminado exitosamente";
        } catch (Exception e) {
            log.error("Error al eliminar tipo con id {}: {}", id, e.getMessage());
            return e.getMessage();
        }
    }

    private TipoDTO convertirDto(Tipo tipo){
        TipoDTO tipoDTO = new TipoDTO();
        tipoDTO.setId(tipo.getId());
        tipoDTO.setNombre(tipo.getNombre());
        return tipoDTO;
    }
}