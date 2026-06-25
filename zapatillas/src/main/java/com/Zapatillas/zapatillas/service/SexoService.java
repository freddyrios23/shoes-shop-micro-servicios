package com.Zapatillas.zapatillas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Zapatillas.zapatillas.DTO.SexoDTO;
import com.Zapatillas.zapatillas.model.Sexo;
import com.Zapatillas.zapatillas.repository.SexoRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;


@Service
@Transactional
@Slf4j
public class SexoService {

    @Autowired 
    private SexoRepository sexoRepository;

    private SexoDTO convertirDTO(Sexo sexo){
        SexoDTO sexoDTO = new SexoDTO();
        sexoDTO.setId(sexo.getId());
        sexoDTO.setGenero(sexo.getGenero());
        return sexoDTO;
    }

    public List<SexoDTO> obtenerTodos(){
        log.info("Obteniendo todos los sexos");
        return sexoRepository.findAll().stream().map(this::convertirDTO).toList();
    }

    public SexoDTO buscarPorId(Integer id){
        log.info("Buscando sexo con id {}", id);
        Sexo sexo = sexoRepository.findById(id)
        .orElseThrow(() -> {
            log.warn("No existe sexo con id {}", id);
            return new RuntimeException("¡Sexo no encontrada!");
        });
        log.info("Sexo encontrado: {}", sexo.getGenero());
        return convertirDTO(sexo);
    }

    public SexoDTO guardarSexo(Sexo sexo){
        log.info("Guardando sexo con genero {}", sexo.getGenero());

        Sexo nuevoSexo = sexoRepository.save(sexo);

        log.info("sexo guardado exitosamente con id: {}",nuevoSexo.getGenero());
        return convertirDTO(nuevoSexo);
    }

    public Sexo actualizarSexo(Integer id, Sexo sexo){
        log.info("Actualizando sexo con id {}", id);
        Sexo sex = sexoRepository.findById(id)
        .orElseThrow(() -> {
            log.warn("No existe sexo con id {}", id);
            return new RuntimeException("¡Sexo no encontrada! no existe en los registros");
        });
        if (sexo.getGenero()!= null) {
            sex.setGenero(sexo.getGenero());
        }
        log.info("Sexo con id {} actualizado correctamente", id);
        return sexoRepository.save(sex);
    }

    public String eliminarSexo(Integer id){
        log.info("Intentando eliminar sexo con id {}", id);
        try {
            Sexo sexo = sexoRepository.findById(id)
            .orElseThrow(() -> {
                log.warn("No existe sexo con id {}", id);
                return new RuntimeException("¡Imposible eliminar! el sexo con el id" + id + "no existe");
            });
            log.info("Sexo encontrado: {}", sexo.getGenero());
            sexoRepository.delete(sexo);
            log.info("Sexo con id {} eliminado correctamente", id);
            return "el sexo '" + sexo.getGenero() + "' ha sido eliminado exitosamente.";
        } catch (Exception e) {
            log.error("Error al eliminar sexo con id {}: {}", id, e.getMessage());
            return e.getMessage();
        }
    }

}

