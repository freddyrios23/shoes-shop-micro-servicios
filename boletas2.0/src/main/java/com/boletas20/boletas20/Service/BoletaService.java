package com.boletas20.boletas20.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boletas20.boletas20.DTO.BoletaDTO;
import com.boletas20.boletas20.Model.Boleta;
import com.boletas20.boletas20.Model.Boletas;
import com.boletas20.boletas20.Repository.BoletaRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class BoletaService {
@Autowired
    private BoletaRepository boletaRepository;

    private BoletaDTO convertirDTO(Boleta boleta) {
        BoletaDTO boletaDto = new BoletaDTO(); 
        boletaDto.setId(boleta.getId());
        boletaDto.setFecha(boleta.getFecha());
        boletaDto.setTotal(boleta.getTotal());
        boletaDto.setCantidad(boleta.getCantidad());
        
        if (boleta.getBoletas() != null) {
            boletaDto.setZapatillasId(boleta.getBoletas().stream().map(Boletas::getId).toList());
        }
        return boletaDto;
    }

    public List<BoletaDTO> obtenerTodas() {
        log.info("Obteniendo todas las Boletas");
        return boletaRepository.findAll().stream().map(this::convertirDTO).toList();
    }

    public BoletaDTO buscarPorId(Integer id) {
        log.info("Buscando boleta con id {}", id);
        Boleta boleta = boletaRepository.findById(id)
            .orElseThrow(() -> {
                log.warn("No existe boleta con id {}", id);
                return new RuntimeException("¡La Boleta no fue encontrada!");
            });
        log.info("Boleta encontrada con id {}", boleta.getId());
        return convertirDTO(boleta);
    }


    public BoletaDTO guardarBoleta(Boleta boleta) {
        log.info("Guardando boleta con fecha {}", boleta.getFecha());

        Boleta entidadGuardada = boletaRepository.save(boleta);

        log.info("Boleta guardada exitosamente con id {}", entidadGuardada.getId());

        return convertirDTO(entidadGuardada);
    }

    public Boleta actualizarBoleta(Integer id, Boleta boleta) {
        log.info("Actualizando boleta con id {}", id);
        Boleta ticket = boletaRepository.findById(id)
            .orElseThrow(() -> {
                log.warn("No existe boleta con id {}", id);
                return new RuntimeException("¡La Boleta no fue encontrada!");
            });
        if (boleta.getFecha() != null) {
            ticket.setFecha(boleta.getFecha());
        }
        if (boleta.getTotal() != null) {
            ticket.setTotal(boleta.getTotal());
        }
        if (boleta.getCantidad() != null) {
            ticket.setCantidad(boleta.getCantidad());
        }
        if (boleta.getBoletas() != null) {
            ticket.setBoletas(boleta.getBoletas());
        }
        return boletaRepository.save(ticket);
    }

    public String eliminarBoleta(Integer id) {
        log.info("Eliminando boleta con id {}", id);
        try {
            Boleta boleta = boletaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("No existe boleta con id {}", id);
                    return new RuntimeException("¡Imposible eliminar! La boleta con el id " + id + " no existe");
                });
            boletaRepository.delete(boleta);
            log.info("Boleta estructura eliminada con id {}", boleta.getId());
            return "La boleta '" + boleta.getId() + "' ha sido eliminada exitosamente";
        } catch (Exception e) {
            log.error("Error al eliminar boleta con id {}", id, e);
            return e.getMessage();
        }
    }

    public BoletaDTO buscarPorZapatilla(Integer zapatillaId) {
        log.info("Buscando boleta asociada a zapatilla con id {}", zapatillaId);

        Boleta boleta = boletaRepository.findFirstByBoletas_Id(zapatillaId)
            .orElseThrow(() -> {
                log.warn("No existe boleta asociada a zapatilla con id {}", zapatillaId);
                return new RuntimeException("¡No se encontró boleta para esa zapatilla!");
            });

        log.info("Boleta encontrada para zapatilla con id {}", zapatillaId);

        return convertirDTO(boleta);
    }
}
