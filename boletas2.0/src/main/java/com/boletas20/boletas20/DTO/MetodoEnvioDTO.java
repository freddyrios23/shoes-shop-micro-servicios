package com.boletas20.boletas20.DTO;

import lombok.Data;

@Data
public class MetodoEnvioDTO {
    private Integer id;
    private String tipo;
    private Integer costo;
    private Integer tiempoEntrega;
    
}
