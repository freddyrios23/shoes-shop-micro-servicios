package com.Zapatillas.zapatillas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.Zapatillas.zapatillas.DTO.BoletaExternaDTO;
import com.Zapatillas.zapatillas.DTO.ZapatillaDTO;
import com.Zapatillas.zapatillas.model.Zapatilla;

import reactor.core.publisher.Mono;

@Service
public class ZapatillasValidaciones {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public BoletaExternaDTO obtenerBoleta(Integer id) {
        BoletaExternaDTO boletaRecuperada = new BoletaExternaDTO();

        try {
            BoletaExternaDTO resultado = webClientBuilder.build()
                .get()
                .uri("http://BOLETAS/api/v1/boletas/" + id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.empty())
                .bodyToMono(BoletaExternaDTO.class)
                .block();

            if (resultado != null) {
                return resultado;
            }

            boletaRecuperada.setId(0);
            boletaRecuperada.setZapatillasId(List.of(id));
            boletaRecuperada.setFecha(null);
            boletaRecuperada.setTotal(-1);
            boletaRecuperada.setCantidad(0);
            return boletaRecuperada;

        } catch (Exception e) {
            boletaRecuperada.setId(0);
            boletaRecuperada.setZapatillasId(List.of(id));
            boletaRecuperada.setFecha(null);
            boletaRecuperada.setTotal(-2);
            boletaRecuperada.setCantidad(0);
            return boletaRecuperada;
        }
    }

    public ZapatillaDTO convertirADTO(Zapatilla zapatilla) {
        ZapatillaDTO dto = new ZapatillaDTO();

        dto.setId(zapatilla.getId());
        dto.setNombre(zapatilla.getNombre());
        dto.setPrecio(zapatilla.getPrecio());

        dto.setMarcaId(zapatilla.getMarca().getId());
        dto.setNombreMarca(zapatilla.getMarca().getNombre());

        dto.setBoleta(obtenerBoleta(zapatilla.getId()));

        return dto;
    }
}