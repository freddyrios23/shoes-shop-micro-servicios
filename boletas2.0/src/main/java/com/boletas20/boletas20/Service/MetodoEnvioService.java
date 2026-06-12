package com.boletas20.boletas20.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boletas20.boletas20.DTO.MetodoEnvioDTO;
import com.boletas20.boletas20.Model.MetodoEnvio;
import com.boletas20.boletas20.Repository.MetodoEnvioRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class MetodoEnvioService {

    @Autowired
    private MetodoEnvioRepository metodoEnvioRepository;

    private MetodoEnvioDTO convertirADTO(MetodoEnvio metodoEnvio) {

        MetodoEnvioDTO metodoEnvioDto = new MetodoEnvioDTO();

        metodoEnvioDto.setId(metodoEnvio.getId());
        metodoEnvioDto.setTipo(metodoEnvio.getTipo());
        metodoEnvioDto.setCosto(metodoEnvio.getCosto());
        metodoEnvioDto.setTiempoEntrega(metodoEnvio.getTiempoEntrega());

        return metodoEnvioDto;
    }

    public List<MetodoEnvioDTO> obtenerTodas(){
        log.info("Obteniendo todos los métodos de envío");
        return metodoEnvioRepository.findAll().stream().map(this::convertirADTO).toList();
    }

    public MetodoEnvioDTO buscarPorId(Integer id){
        log.info("Buscando método de envío con id {}", id);
        MetodoEnvio metodoEnvio = metodoEnvioRepository.findById(id)
        .orElseThrow(() -> {
            log.warn("No existe método de envío con id {}", id);
            return new RuntimeException("¡El método de envío no encontrado!");
        });
        log.info("Método de envío encontrado: {}", metodoEnvio.getTipo());
        return convertirADTO(metodoEnvio);
    } 

    public MetodoEnvio guardarMetodoEnvio (MetodoEnvio metodoEnvio){
        log.info("Guardando método de envío con tipo {}", metodoEnvio.getTipo());
        return metodoEnvioRepository.save(metodoEnvio);
    }

    public MetodoEnvio actualizarMetodoEnvio(Integer id,MetodoEnvio metodoEnvio){
        log.info("Actualizando método de envío con id {}", id);
        MetodoEnvio metodoEnvio2 = metodoEnvioRepository.findById(id).orElseThrow(()-> {
            log.warn("No existe método de envío con id {}", id);
            return new RuntimeException("¡El método de envío no encontrado!");
        });
        if (metodoEnvio.getTipo()!= null) {
            metodoEnvio2.setTipo(metodoEnvio.getTipo());
        }
        if (metodoEnvio.getCosto()!= null) {
            metodoEnvio2.setCosto(metodoEnvio.getCosto());
        }
        if (metodoEnvio.getTiempoEntrega()!= null) {
            metodoEnvio2.setTiempoEntrega(metodoEnvio.getTiempoEntrega());
        }
        log.info("Método de envío con id {} actualizado correctamente", id);
        return metodoEnvioRepository.save(metodoEnvio2);
    }

    public String eliminarMetodoEnvio(Integer id){
        log.info("Intentando eliminar método de envío con id {}", id);
        try {
            MetodoEnvio metodoEnvio = metodoEnvioRepository.findById(id).orElseThrow(()-> {
                log.warn("No existe método de envío con id {}", id);
                return new RuntimeException("¡Imposible eliminar! Método de envío con el id " + id + " no existe");
            });
            metodoEnvioRepository.delete(metodoEnvio);
            log.info("Método de envío con id {} eliminado correctamente", id);
            return "EL envio '" + metodoEnvio.getId() + "' ha sido eliminada exitosamente";
        } catch (Exception e) {
            log.error("Error al eliminar método de envío con id {}: {}", id, e.getMessage());
            return e.getMessage();
        }
    }
}
