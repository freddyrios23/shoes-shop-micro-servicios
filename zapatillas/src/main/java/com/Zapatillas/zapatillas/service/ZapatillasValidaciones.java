package com.Zapatillas.zapatillas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.Zapatillas.zapatillas.DTO.BoletaExternaDTO;

import reactor.core.publisher.Mono;

@Service
public class ZapatillasValidaciones {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public BoletaExternaDTO obtenerBoleta(Integer id) {
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

            return crearBoletaFallback(id, -1);

        } catch (Exception e) {
            return crearBoletaFallback(id, -2);
        }
    }

    private BoletaExternaDTO crearBoletaFallback(Integer id, Integer total) {
        BoletaExternaDTO boletaRecuperada = new BoletaExternaDTO();

        boletaRecuperada.setId(0);
        boletaRecuperada.setZapatillasId(id);
        boletaRecuperada.setFecha(null);
        boletaRecuperada.setTotal(total);
        boletaRecuperada.setCantidad(0);

        return boletaRecuperada;
    }
}