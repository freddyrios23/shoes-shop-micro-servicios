package com.Zapatillas.zapatillas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Zapatillas.zapatillas.DTO.ColorDTO;
import com.Zapatillas.zapatillas.model.Color;
import com.Zapatillas.zapatillas.repository.ColorRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ColorService {

    @Autowired
    private ColorRepository colorRepository;

    private ColorDTO convertirDTO(Color color){
        ColorDTO colorDTO = new ColorDTO();
        colorDTO.setId(color.getId());
        colorDTO.setNombre(color.getNombre());
        return colorDTO;
    }

    public List<ColorDTO> obtenerTodos(){
        log.info("Obteniendo todos los colores");
        return colorRepository.findAll().stream().map(this::convertirDTO).toList();

    }

    public ColorDTO buscarPorId(Integer id){
        log.info("Buscando color con id {}", id);
        Color color = colorRepository.findById(id)
        .orElseThrow(() -> {
            log.warn("No existe color con id {}", id);
            return new RuntimeException("¡El color no encontrado!");
        });
        log.info("Color encontrado: {}", color.getNombre());
        return convertirDTO(color);
    }

    public Color guardarColor(Color color){
        log.info("Guardando color con nombre {}", color.getNombre());
        return colorRepository.save(color);
    }

    public Color actualizarColor(Integer id, Color color){
        log.info("Actualizando color con id {}", id);
        Color colorDB = colorRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("No existe color con id {}", id);
                    return new RuntimeException("¡El color no encontrado!");
                });

        if (color.getNombre() != null) {
            colorDB.setNombre(color.getNombre());
        }
        log.info("Color con id {} actualizado correctamente", id);
        return colorRepository.save(colorDB);
    }

    public String eliminarColor(Integer id){
        log.info("Intentando eliminar color con id {}", id);
        Color color = colorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("¡Imposible eliminar! Color con el id " + id + " no existe"));

        colorRepository.delete(color);

        log.info("Color con id {} eliminado correctamente", id);
        return "El color '" + color.getNombre() + "' ha sido eliminado exitosamente";
    }
}
