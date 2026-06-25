package com.Zapatillas.zapatillas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Zapatillas.zapatillas.DTO.MaterialDTO;
import com.Zapatillas.zapatillas.model.Material;
import com.Zapatillas.zapatillas.repository.MaterialRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class MaterialService {
    @Autowired
    private MaterialRepository materialRepository;

    private MaterialDTO convertirDTO (Material material){
        MaterialDTO materialDto = new MaterialDTO();
        materialDto.setId(material.getId());
        materialDto.setNombre(material.getNombre());
        return materialDto;
    }
    public List<MaterialDTO> obtenerTodos(){
        log.info("Obteniendo todos los materiales");
        return materialRepository.findAll().stream().map(this::convertirDTO).toList();
    }

    public MaterialDTO buscarPorId(Integer id){
        log.info("Buscando material con id {}", id);
        Material material=materialRepository.findById(id)
        .orElseThrow(()-> {
            log.warn("No existe material con id {}", id);
            return new RuntimeException("¡El material no encontrado!");
        });
        log.info("Material encontrado: {}", material.getNombre());
        return convertirDTO(material);
    }

    public MaterialDTO guardarMaterial(Material material){
        log.info("Guardando material con nombre {}", material.getNombre());
        Material nuevoMaterial = materialRepository.save(material);

        log.info("Material guardado exitosamente con el id {}",nuevoMaterial.getId());
        return convertirDTO(nuevoMaterial);
    }

    public Material actualizarMaterial (Integer id,Material material){  
        log.info("Actualizando material con id {}", id);
        Material mater = materialRepository.findById(id).orElseThrow(()-> {
            log.warn("No existe material con id {}", id);
            return new RuntimeException("¡El material no encontrado!");
        });
        if (material.getNombre()!=null) {
            mater.setNombre(material.getNombre());
        }
        log.info("Material con id {} actualizado correctamente", id);
        return materialRepository.save(mater);
    }

    public String eliminarMaterial(Integer id){
        log.info("Intentando eliminar material con id {}", id);
        try {
            Material material = materialRepository.findById(id).orElseThrow(()-> {
                log.warn("No existe material con id {}", id);
                return new RuntimeException("¡Imposible eliminar! Material con el id " + id + " no existe");
            });
            materialRepository.delete(material);
            log.info("Material con id {} eliminado correctamente", id);
            return "el material '" + material.getId() + "' ha sido eliminado exitosamente";
        } catch (Exception e) {
            log.error("Error al eliminar material con id {}: {}", id, e.getMessage());
            return e.getMessage();
        }
    }

}