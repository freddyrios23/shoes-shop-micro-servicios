package com.cliente.cliente.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cliente.cliente.DTO.ClienteDTO;
import com.cliente.cliente.model.Cliente;
import com.cliente.cliente.repository.ClienteRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public ClienteDTO convertirDTO(Cliente cliente){
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(cliente.getId());
        clienteDTO.setNombre(cliente.getNombre());
        clienteDTO.setTelefono(cliente.getTelefono());
        
        return clienteDTO;
    }

    public List<ClienteDTO> obtenertodos(){
        log.info("Obteniendo todos los clientes");
        return clienteRepository.findAll().stream().map(this::convertirDTO).toList();
    }

    public ClienteDTO buscarPorId(Integer id){
        log.info("Buscando cliente con id {}", id);
        Cliente cliente = clienteRepository.findById(id)
        .orElseThrow(()-> {
            log.warn("No existe cliente con id {}", id);
            return new RuntimeException("¡cliente no encontrado!");
        });
        log.info("Cliente encontrado: {}", cliente.getNombre());
        return convertirDTO(cliente);
    }

    public Cliente guardarCliente(Cliente cliente){
        log.info("Guardando cliente con nombre {}", cliente.getNombre());
        Cliente nuevoCliente = clienteRepository.save(cliente);
        log.info("Cliente guardado exitosamente con id {}", nuevoCliente.getId());
        return nuevoCliente;
    }

    public Cliente actualizarCliente(Integer id,Cliente cliente){
        log.info("Actualizando cliente con id {}", id);
        Cliente client = clienteRepository.findById(id)
        .orElseThrow(()-> {
            log.warn("No existe cliente con id {}", id);
            return new RuntimeException("¡cliente no encontrado!");
        });

        if (cliente.getRut()!=null) {
            client.setRut(cliente.getRut());
        }
        if (cliente.getNombre()!=null) {
            client.setNombre(cliente.getNombre());
        }
        if (cliente.getTelefono()!=null) {
            client.setTelefono(cliente.getTelefono());
        }
        
        Cliente clienteActualizado = clienteRepository.save(client);
        log.info("Cliente con id {} actualizado correctamente", id);
        return clienteActualizado;
    }

    public String eliminarCliente(Integer id){
        log.info("Intentando eliminar cliente con id {}", id);
        try {
            Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(()-> {
                log.warn("No existe cliente con id {}", id);
                return new RuntimeException("¡Imposible eliminar! El cliente con el id" + id + "no existe");
            });
            clienteRepository.delete(cliente);
            log.info("Cliente con id {} eliminado correctamente", id);
            return "El cliente '" + cliente.getId() + "' ha sido eliminado exitosamente";
        } catch (Exception e) {
            log.error("Error al intentar eliminar cliente con id {}", id, e);
            return e.getMessage();
        }
    }

}

