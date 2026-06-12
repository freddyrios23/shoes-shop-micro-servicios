package com.boletas20.boletas20.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boletas20.boletas20.DTO.MetodoPagoDTO;
import com.boletas20.boletas20.Model.MetodoPago;
import com.boletas20.boletas20.Repository.MetodoPagoRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class MetodoPagoService {

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    private MetodoPagoDTO convertirADTO(MetodoPago metodoPago){
        MetodoPagoDTO metodoPagoDTO = new MetodoPagoDTO();
        metodoPagoDTO.setId(metodoPago.getId());
        metodoPago.setTipo(metodoPago.getTipo());
        return metodoPagoDTO;
    }

    public List<MetodoPagoDTO> obtenerTodos(){
        log.info("Obteniendo todos los métodos de pago");
        return metodoPagoRepository.findAll().stream().map(this::convertirADTO).toList();
    }

    public MetodoPagoDTO buscarPorId(Integer id){
        log.info("Buscando método de pago con id {}", id);
        MetodoPago metodoPago = metodoPagoRepository.findById(id).orElseThrow(()-> {
            log.warn("No existe método de pago con id {}", id);
            return new RuntimeException("¡El método de pago no encontrado!");
        });
        log.info("Método de pago encontrado: {}", metodoPago.getTipo());
        return convertirADTO(metodoPago);
    }

    public MetodoPago guardarMetodoPago(MetodoPago metodoPago){
        log.info("Guardando método de pago con tipo {}", metodoPago.getTipo());
        return metodoPagoRepository.save(metodoPago);
    }

    public MetodoPago actualizarMetodoPago(Integer id,MetodoPago metodoPago){
        log.info("Actualizando método de pago con id {}", id);
        MetodoPago metodoPago2 = metodoPagoRepository.findById(id).orElseThrow(()-> {
            log.warn("No existe método de pago con id {}", id);
            return new RuntimeException("¡El método de pago no encontrado!");
        });
        if (metodoPago.getTipo()!= null) {
            metodoPago2.setTipo(metodoPago.getTipo());
        }
        log.info("Método de pago con id {} actualizado correctamente", id);
        return metodoPagoRepository.save(metodoPago2);
    }

    public String eliminarMetodoPAgo(Integer id){
        log.info("Intentando eliminar método de pago con id {}", id);
        try {
            MetodoPago metodoPago = metodoPagoRepository.findById(id).orElseThrow(()-> {
                log.warn("No existe método de pago con id {}", id);
                return new RuntimeException("¡Imposible eliminar! Método de pago con el id " + id + " no existe");
            });
            metodoPagoRepository.delete(metodoPago);
            log.info("Método de pago con id {} eliminado correctamente", id);
            return "El metodo de pago '" + metodoPago.getId() + "' ha sido eliminado exitosamente";
        } catch (Exception e) {
            log.error("Error al eliminar método de pago con id {}: {}", id, e.getMessage());
            return e.getMessage();
        }
    }
}
