package com.Zapatillas.zapatillas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Zapatillas.zapatillas.DTO.ZapatillaDTO;
import com.Zapatillas.zapatillas.model.Zapatilla;
import com.Zapatillas.zapatillas.repository.ZapatillaRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ZapatillaService {

    @Autowired
    private ZapatillaRepository zapatillaRepository;

    public List<ZapatillaDTO> obtenerTodas(){
        log.info("Obteniendo todas las zapatillas");
        return zapatillaRepository.findAll().stream().map(this::convertirADTO).toList();
    }

    public ZapatillaDTO buscarPorId(Integer id){
        log.info("Buscando zapatilla con id {}",id);
        Zapatilla zapatilla = zapatillaRepository.findById(id)
        .orElseThrow(()-> {
            log.warn("No existe zapatilla con id {}", id);
            return new RuntimeException("¡La Zapatilla no encontrada!");
        });
        log.info("Zapatilla encontrada: {}", zapatilla.getNombre());
        return convertirADTO(zapatilla);
    }

    public Zapatilla guardarZapatilla(Zapatilla zapatilla){
        log.info("Guardando zapatilla {}",zapatilla.getNombre());

        Zapatilla nuevaZapatilla = zapatillaRepository.save(zapatilla);

        log.info("Zapatilla guardada exitosamente con id {}",nuevaZapatilla.getId());

        return nuevaZapatilla;
    }

    public Zapatilla actualizarZapatilla(Integer id, Zapatilla zapatilla) {

        log.info("Actualizando zapatilla con id {}", id);

        Zapatilla zapato = zapatillaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("No existe zapatilla con id {}", id);
                    return new RuntimeException("¡La zapatilla no existe en los registros!");
                });

        if (zapatilla.getNombre() != null) {
            zapato.setNombre(zapatilla.getNombre());
        }

        if (zapatilla.getPrecio() != null) {
            zapato.setPrecio(zapatilla.getPrecio());
        }

        if (zapatilla.getMarca() != null) {
            zapato.setMarca(zapatilla.getMarca());
        }

        Zapatilla zapatillaActualizada = zapatillaRepository.save(zapato);

        log.info("Zapatilla con id {} actualizada correctamente", id);
        return zapatillaActualizada;
    }

    public String eliminarZapatilla(Integer id){

    log.info("Intentando eliminar zapatilla con id {}", id);

    try {
        Zapatilla zapatilla = zapatillaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("No existe zapatilla con id {}", id);
                    return new RuntimeException(
                            "¡Imposible eliminar! La zapatilla con el id " + id + " no existe");
                });

        zapatillaRepository.delete(zapatilla);

        log.info("Zapatilla eliminada correctamente: {}", zapatilla.getNombre());

        return "La zapatilla '" + zapatilla.getNombre() + "' ha sido eliminada exitosamente.";

    } catch (Exception e) {
        log.error("Error al eliminar zapatilla con id {}", id, e);
        return e.getMessage();
        }
    }

    private ZapatillaDTO convertirADTO (Zapatilla zapatilla){
        ZapatillaDTO zapatillaDTO = new ZapatillaDTO();
        zapatillaDTO.setId(zapatilla.getId());
        zapatillaDTO.setNombre(zapatilla.getNombre());
        zapatillaDTO.setPrecio(zapatilla.getPrecio());

        if (zapatilla.getMarca()!=null) {
            zapatillaDTO.setMarcaId(zapatilla.getMarca().getId());
            zapatillaDTO.setNombreMarca(zapatilla.getMarca().getNombre());
        }
        return zapatillaDTO;   
    }
}

