package com.cliente.cliente.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cliente.cliente.DTO.RegionDTO;
import com.cliente.cliente.model.Region;
import com.cliente.cliente.repository.RegionRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    private RegionDTO convertirDTO(Region region) {
        RegionDTO dto = new RegionDTO();
        dto.setId(region.getId());
        dto.setNombre(region.getNombre());
        return dto;
    }

    public List<RegionDTO> obtenerTodas() {
        log.info("Obteniendo todas las regiones");
        return regionRepository.findAll().stream().map(this::convertirDTO).toList();
    }

    public RegionDTO buscarPorId(Integer id) {
        log.info("Buscando región con id {}", id);
        Region region = regionRepository.findById(id)
        .orElseThrow(() -> {
            log.warn("No existe región con id {}", id);
            return new RuntimeException("Región no encontrada");
        });
        log.info("Región encontrada: {}", region.getNombre());
        return convertirDTO(region);
    }

    public Region guardarRegion(Region region) {
        log.info("Guardando región con nombre {}", region.getNombre());
        return regionRepository.save(region);
    }

    public Region actualizarRegion(Integer id, Region region) {
        log.info("Actualizando región con id {}", id);
        Region regionEditada = regionRepository.findById(id)
        .orElseThrow(() -> {
            log.warn("No existe región con id {}", id);
            return new RuntimeException("¡La región no encontrada!");
        });
        log.info("Región encontrada: {}", regionEditada.getNombre());

        if (region.getNombre() != null) {
            regionEditada.setNombre(region.getNombre());
        }
        log.info("Región con id {} actualizada correctamente", id);
        return regionRepository.save(regionEditada);
    }

    public String eliminarRegion(Integer id) {
        log.info("Intentando eliminar región con id {}", id);
        try {
            Region region = regionRepository.findById(id)
            .orElseThrow(() -> {
                log.warn("No existe región con id {}", id);
                return new RuntimeException("¡Imposible eliminar! Región con el id " + id + " no existe");
            });

            log.info("Región encontrada: {}", region.getNombre());
            regionRepository.delete(region);

            return "La región '" + region.getNombre() + "' fue eliminada correctamente";
        } catch (Exception e) {
            log.error("Error al eliminar región con id {}: {}", id, e.getMessage());
            return e.getMessage();
        }
    }
}